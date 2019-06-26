package br.com.appbarbearia.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.appbarbearia.model.Endereco;
import br.com.appbarbearia.util.JdbcRepository;

@Repository
public class EnderecoRepository extends JdbcRepository<Endereco> implements RowMapper<Endereco>{
	
	Logger LOGGER = Logger.getLogger(EnderecoRepository.class.getName());
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Endereco mapRow(ResultSet rs, int rowNum) throws SQLException {
		Endereco e = new Endereco();
		setValues(e, rs);
		return e;
	}
	
	public Optional<Endereco> save(Endereco endereco) {
		return save(endereco, false);
	}

	@CacheEvict(value = "enderecos", allEntries = true)
	public Optional<Endereco> save(final Endereco endereco, boolean refresh) {
		Set<ConstraintViolation<Endereco>> cvs = validate(endereco);
		if (cvs.isEmpty()) {
			final boolean novo = (endereco.getCodigo() == 0);
			return (novo ? saveNew(endereco, refresh) : saveOld(endereco, refresh));
		} else {
			StringBuilder sb = new StringBuilder();
			cvs.forEach(cv -> {
				if (sb.length() > 0) {
					sb.append("\n");
				}
				sb.append(cv.getMessage());
			});
			throw new ValidationException(sb.toString());
		}
	}

	private Optional<Endereco> saveOld(final Endereco endereco, boolean refresh) {
		String sql = getUpdate();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql);
				int idx = setValuesOnStatement(endereco, stmt);
				stmt.setLong(idx++, endereco.getCodigo());
				return stmt;
			}
		});
		if (row > 0) {
			if (refresh) {
				Optional<Endereco> opEndereco = findByCodigo(endereco.getCodigo());
				LOGGER.log(Level.INFO, "Alterou {0}", (opEndereco.isPresent() ? opEndereco.get() : endereco));
				return opEndereco;
			} else {
				LOGGER.log(Level.INFO, "Alterou {0}", endereco);
			}
		} else {
			LOGGER.log(Level.INFO, "Não alterou {0}", endereco);
		}
		return Optional.of(endereco);
	}

	private Optional<Endereco> saveNew(final Endereco endereco, boolean refresh) {
		String sql = getInsert();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				setValuesOnStatement(endereco, stmt);
				return stmt;
			}
		}, keyHolder);

		endereco.setCodigo(keyHolder.getKey().intValue());

		if (row > 0) {
			if (refresh) {
				Optional<Endereco> opEndereco = findByCodigo(endereco.getCodigo());
				LOGGER.log(Level.INFO, "Criou {0}", (opEndereco.isPresent() ? opEndereco.get() : endereco));
				return opEndereco;
			} else {
				LOGGER.log(Level.INFO, "Criou {0}", endereco);
			}
		} else {
			LOGGER.log(Level.INFO, "Não criou {0}", endereco);
		}
		return Optional.of(endereco);
	}
	
	private int setValuesOnStatement(Endereco e, PreparedStatement stmt) throws SQLException {
		int idx = 1;
		stmt.setInt(idx++, e.getCodigoCidade());
		stmt.setString(idx++, e.getEndereco());
		stmt.setInt(idx++, e.getNumero());
		stmt.setString(idx++, e.getCep());
		return idx;
	}
	
	protected void setValues(Endereco e, ResultSet rs) throws SQLException {
		e.setCodigo(rs.getInt("CODIGO"));
		e.setCodigoCidade(rs.getInt("CODIGO_CIDADE"));
		e.setEndereco(rs.getString("ENDERECO"));
		e.setNumero(rs.getInt("NUMERO"));
		e.setCep(rs.getString("CEP"));
		e.setCadastro(rs.getDate("CADASTRO"));
		e.setAlterado(rs.getDate("ALTERADO"));
	}
	
	@CacheEvict(value = "enderecos", allEntries = true)
	public int delete(final Endereco endereco) {
		Integer result = jdbcTemplate.execute(getDelete(), new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, endereco.getCodigo());
				return stmt.executeUpdate();
			}
		});

		int r = (result == null ? 0 : result.intValue());
		if (r > 0) {
			LOGGER.log(Level.INFO, "Removeu {0}", endereco);
		} else {
			LOGGER.log(Level.INFO, "Não removeu {0}", endereco);
		}

		return r;
	}

	public Optional<Endereco> findByCodigo(int codigo) {
		String query = getSelect() + " WHERE CODIGO=?";
		Object[] args = new Object[] { codigo };
		return find(query, args);
	}

	private Optional<Endereco> find(String query, Object[] args) {
		List<Endereco> result = jdbcTemplate.query(query, args, this);
		if (result == null || result.isEmpty()) {
			return Optional.empty();
		} else {
			Endereco endereco = (Endereco) result.get(0);
			return Optional.ofNullable(endereco);
		}
	}

	@Cacheable(value = "enderecos")
	public List<Endereco> findAll() {
		return jdbcTemplate.query(getSelect(), this);
	}
	
	private String getSelect() {
		return "SELECT E.CODIGO, E.CODIGO_CIDADE, E.ENDERECO, E.NUMERO, E.CEP, E.CADASTRO, E.ALTERADO FROM ENDERECO E";
	}
	
	private String getInsert() {
		return "INSERT INTO ENDERECO (CODIGO_CIDADE, ENDERECO, NUMERO, CEP, CADASTRO, ALTERADO) VALUES (?,?,?,?,NOW(),NOW())";
	}
	
	private String getUpdate() {
		return "UPDATE ENDERECO SET CODIGO_CIDADE=?, ENDERECO=?, NUMERO=?, CEP=?, ALTERADO=NOW() WHERE CODIGO = ?";
	}
	
	private String getDelete() {
		return "DELETE FROM ENDERECO WHERE CODIGO = ?";
	}
}
