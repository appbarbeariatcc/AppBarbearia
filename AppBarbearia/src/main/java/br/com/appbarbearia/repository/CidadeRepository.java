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
import org.springframework.transaction.annotation.Transactional;

import br.com.appbarbearia.model.Cidade;
import br.com.appbarbearia.model.Estados;
import br.com.appbarbearia.util.JdbcRepository;

@Transactional
@Repository
public class CidadeRepository extends JdbcRepository<Cidade> implements RowMapper<Cidade> {

	Logger LOGGER = Logger.getLogger(BarbeiroRepository.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Cidade mapRow(ResultSet rs, int rowNum) throws SQLException {
		Cidade c = new Cidade();
		setValues(c, rs);
		return c;
	}

	public Optional<Cidade> save(Cidade cidade) {
		return save(cidade, false);
	}

	@CacheEvict(value = "cidades", allEntries = true)
	public Optional<Cidade> save(final Cidade cidade, boolean refresh) {
		Set<ConstraintViolation<Cidade>> cvs = validate(cidade);
		if (cvs.isEmpty()) {
			final boolean novo = (cidade.getCodigo() == 0);
			return (novo ? saveNew(cidade, refresh) : saveOld(cidade, refresh));
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

	private Optional<Cidade> saveOld(final Cidade Cidade, boolean refresh) {
		String sql = getUpdate();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql);
				int idx = setValuesOnStatement(Cidade, stmt);
				stmt.setLong(idx++, Cidade.getCodigo());
				return stmt;
			}
		});
		if (row > 0) {
			if (refresh) {
				Optional<Cidade> opCidade = findByCodigo(Cidade.getCodigo());
				LOGGER.log(Level.INFO, "Alterou {0}", (opCidade.isPresent() ? opCidade.get() : Cidade));
				return opCidade;
			} else {
				LOGGER.log(Level.INFO, "Alterou {0}", Cidade);
			}
		} else {
			LOGGER.log(Level.INFO, "Não alterou {0}", Cidade);
		}
		return Optional.of(Cidade);
	}

	private Optional<Cidade> saveNew(final Cidade cidade, boolean refresh) {
		String sql = getInsert();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				setValuesOnStatement(cidade, stmt);
				return stmt;
			}
		}, keyHolder);

		cidade.setCodigo(keyHolder.getKey().intValue());

		if (row > 0) {
			if (refresh) {
				Optional<Cidade> opBarbeiro = findByCodigo(cidade.getCodigo());
				LOGGER.log(Level.INFO, "Criou {0}", (opBarbeiro.isPresent() ? opBarbeiro.get() : cidade));
				return opBarbeiro;
			} else {
				LOGGER.log(Level.INFO, "Criou {0}", cidade);
			}
		} else {
			LOGGER.log(Level.INFO, "Não criou {0}", cidade);
		}
		return Optional.of(cidade);
	}

	private int setValuesOnStatement(Cidade c, PreparedStatement stmt) throws SQLException {
		int idx = 1;
		stmt.setString(idx++, c.getNome());
		stmt.setString(idx++, c.getEstado().toString());
		return idx;
	}

	protected void setValues(Cidade c, ResultSet rs) throws SQLException {
		c.setCodigo(rs.getInt("CODIGO"));
		c.setNome(rs.getString("NOME"));
		c.setEstado(Estados.valueOf(rs.getString("ESTADO")));
		c.setCadastro(rs.getDate("CADASTRO"));
		c.setAlterado(rs.getDate("ALTERADO"));
	}

	@CacheEvict(value = "cidades", allEntries = true)
	public int delete(final Cidade cidade) {
		Integer result = jdbcTemplate.execute(getDelete(), new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, cidade.getCodigo());
				return stmt.executeUpdate();
			}
		});

		int r = (result == null ? 0 : result.intValue());
		if (r > 0) {
			LOGGER.log(Level.INFO, "Removeu {0}", cidade);
		} else {
			LOGGER.log(Level.INFO, "Não removeu {0}", cidade);
		}

		return r;
	}

	public Optional<Cidade> findByCodigo(int codigo) {
		String query = getSelect() + " WHERE CODIGO=?";
		Object[] args = new Object[] { codigo };
		return find(query, args);
	}

	private Optional<Cidade> find(String query, Object[] args) {
		List<Cidade> result = jdbcTemplate.query(query, args, this);
		if (result == null || result.isEmpty()) {
			return Optional.empty();
		} else {
			Cidade cidade = (Cidade) result.get(0);
			return Optional.ofNullable(cidade);
		}
	}

	@Cacheable(value = "barbeiros")
	public List<Cidade> findAll() {
		return jdbcTemplate.query(getSelect(), this);
	}

	private String getSelect() {
		return "SELECT C.CODIGO, C.NOME, C.ESTADO, C.CADASTRO, C.ALTERADO FROM CIDADE C";
	}

	private String getInsert() {
		return "INSERT INTO CIDADE (NOME, ESTADO, CADASTRO, ALTERADO) VALUES (?,?,NOW(),NOW())";
	}

	private String getUpdate() {
		return "UPDATE CIDADE SET NOME=?, ESTADO=?, ALTERADO=NOW() WHERE CODIGO = ?";
	}

	private String getDelete() {
		return "DELETE FROM CIDADE WHERE CODIGO = ?";
	}
}
