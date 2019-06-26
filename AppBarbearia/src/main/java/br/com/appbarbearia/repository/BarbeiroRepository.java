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

import br.com.appbarbearia.model.Barbeiro;
import br.com.appbarbearia.util.JdbcRepository;

@Transactional
@Repository
public class BarbeiroRepository extends JdbcRepository<Barbeiro> implements RowMapper<Barbeiro> {

	Logger LOGGER = Logger.getLogger(BarbeiroRepository.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Barbeiro mapRow(ResultSet rs, int rowNum) throws SQLException {
		Barbeiro b = new Barbeiro();
		setValues(b, rs);
		return b;
	}

	public Optional<Barbeiro> save(Barbeiro barbeiro) {
		return save(barbeiro, false);
	}

	@CacheEvict(value = "barbeiros", allEntries = true)
	public Optional<Barbeiro> save(final Barbeiro barbeiro, boolean refresh) {
		Set<ConstraintViolation<Barbeiro>> cvs = validate(barbeiro);
		if (cvs.isEmpty()) {
			final boolean novo = (barbeiro.getCodigo() == 0);
			return (novo ? saveNew(barbeiro, refresh) : saveOld(barbeiro, refresh));
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

	private Optional<Barbeiro> saveOld(final Barbeiro barbeiro, boolean refresh) {
		String sql = getUpdate();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql);
				int idx = setValuesOnStatement(barbeiro, stmt);
				stmt.setLong(idx++, barbeiro.getCodigo());
				return stmt;
			}
		});
		if (row > 0) {
			if (refresh) {
				Optional<Barbeiro> opBarbeiro = findByCodigo(barbeiro.getCodigo());
				LOGGER.log(Level.INFO, "Alterou {0}", (opBarbeiro.isPresent() ? opBarbeiro.get() : barbeiro));
				return opBarbeiro;
			} else {
				LOGGER.log(Level.INFO, "Alterou {0}", barbeiro);
			}
		} else {
			LOGGER.log(Level.INFO, "Não alterou {0}", barbeiro);
		}
		return Optional.of(barbeiro);
	}

	private Optional<Barbeiro> saveNew(final Barbeiro barbeiro, boolean refresh) {
		String sql = getInsert();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				setValuesOnStatement(barbeiro, stmt);
				return stmt;
			}
		}, keyHolder);

		barbeiro.setCodigo(keyHolder.getKey().intValue());

		if (row > 0) {
			if (refresh) {
				Optional<Barbeiro> opBarbeiro = findByCodigo(barbeiro.getCodigo());
				LOGGER.log(Level.INFO, "Criou {0}", (opBarbeiro.isPresent() ? opBarbeiro.get() : barbeiro));
				return opBarbeiro;
			} else {
				LOGGER.log(Level.INFO, "Criou {0}", barbeiro);
			}
		} else {
			LOGGER.log(Level.INFO, "Não criou {0}", barbeiro);
		}
		return Optional.of(barbeiro);
	}

	private int setValuesOnStatement(Barbeiro b, PreparedStatement stmt) throws SQLException {
		int idx = 1;
		stmt.setLong(idx++, b.getCodigoBarbearia());
		stmt.setString(idx++, b.getNome());
		stmt.setString(idx++, b.getRg());
		stmt.setString(idx++, b.getCpf());
		setNullSafe(stmt, b.getFoto(), idx++);
		setNullSafe(stmt, b.getDataNascimento(), idx++);
		return idx;
	}

	protected void setValues(Barbeiro b, ResultSet rs) throws SQLException {
		b.setCodigo(rs.getInt("CODIGO"));
		b.setCodigoBarbearia(rs.getLong("CODIGO_BARBEARIA"));
		b.setCodigoCidade(rs.getInt("CODIGO_CIDADE"));
		b.setNome(rs.getString("NOME"));
		b.setRg(rs.getString("RG"));
		b.setCpf(rs.getString("CPF"));
		b.setTelefone(rs.getInt("TELEFONE"));
		b.setCelular(rs.getInt("CELULAR"));
		b.setFoto(rs.getString("FOTO"));
		b.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
		b.setCadastro(rs.getDate("CADASTRO"));
		b.setAlterado(rs.getDate("ALTERADO"));
	}

	@CacheEvict(value = "barbeiros", allEntries = true)
	public int delete(final Barbeiro barbeiro) {
		Integer result = jdbcTemplate.execute(getDelete(), new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException {
				stmt.setLong(1, barbeiro.getCodigo());
				return stmt.executeUpdate();
			}
		});

		int r = (result == null ? 0 : result.intValue());
		if (r > 0) {
			LOGGER.log(Level.INFO, "Removeu {0}", barbeiro);
		} else {
			LOGGER.log(Level.INFO, "Não removeu {0}", barbeiro);
		}

		return r;
	}

	public Optional<Barbeiro> findByCodigo(Long codigo) {
		String query = getSelect() + " WHERE CODIGO=?";
		Object[] args = new Object[] { codigo };
		return find(query, args);
	}

	private Optional<Barbeiro> find(String query, Object[] args) {
		List<Barbeiro> result = jdbcTemplate.query(query, args, this);
		if (result == null || result.isEmpty()) {
			return Optional.empty();
		} else {
			Barbeiro b = (Barbeiro) result.get(0);
			return Optional.ofNullable(b);
		}
	}

	@Cacheable(value = "barbeiros")
	public List<Barbeiro> findAll() {
		return jdbcTemplate.query(getSelect(), this);
	}

	public String getSelect() {
		return "SELECT B.CODIGO, B.CODIGO_BARBEARIA, B.CODIGO_CIDADE, B.NOME, B.RG, B.CPF, B.TELEFONE, B.CELULAR, B.FOTO, B.DATA_NASCIMENTO, B.CADASTRO, B.ALTERADO FROM BARBEIRO B";
	}

	public String getInsert() {
		return "INSERT INTO BARBEIRO (CODIGO_BARBEARIA, CODIGO_CIDADE, NOME, RG, CPF, TELEFONE, CELULAR, FOTO, DATA_NASCIMENTO, CADASTRO, ALTERADO VALUES(?,?,?,?,?,?,?,?,?,NOW(),NOW())";
	}

	public String getUpdate() {
		return "UPDATE BARBEIRO SET CODIGO_BARBEARIA=?,NOME=?,RG=?,CPF=?,DATA_NASCIMENTO=?,ALTERADO=NOW() WHERE CODIGO =?";
	}

	public String getDelete() {
		return "DELETE FROM BARBEIRO WHERE CODIGO=?";
	}
}
