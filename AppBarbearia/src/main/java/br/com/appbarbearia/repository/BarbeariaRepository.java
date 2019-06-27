package br.com.appbarbearia.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;
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

import br.com.appbarbearia.model.Barbearia;
import br.com.appbarbearia.util.JdbcRepository;

@Transactional
@Repository
public class BarbeariaRepository extends JdbcRepository<Barbearia> implements RowMapper<Barbearia> {

	Logger LOGGER = Logger.getLogger(BarbeariaRepository.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Barbearia mapRow(ResultSet rs, int rowNum) throws SQLException {
		Barbearia b = new Barbearia();
		setValues(b, rs);
		return b;
	}

	public Optional<Barbearia> save(Barbearia barbearia) {
		return save(barbearia, false);
	}

	@CacheEvict(value = "barbearias", allEntries = true)
	public Optional<Barbearia> save(final Barbearia barbearia, boolean refresh) {
		Set<ConstraintViolation<Barbearia>> cvs = validate(barbearia);
		if (cvs.isEmpty()) {
			final boolean novo = (barbearia.getCodigo() == 0);
			return (novo ? saveNew(barbearia, refresh) : saveOld(barbearia, refresh));
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

	private Optional<Barbearia> saveOld(final Barbearia barbearia, boolean refresh) {
		String sql = getUpdate();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql);
				int idx = setValuesOnStatement(barbearia, stmt);
				stmt.setLong(idx++, barbearia.getCodigo());
				return stmt;
			}
		});
		if (row > 0) {
			if (refresh) {
				Optional<Barbearia> opBarbearia = findByCodigo(barbearia.getCodigo());
				LOGGER.log(Level.INFO, "Alterou {0}", (opBarbearia.isPresent() ? opBarbearia.get() : barbearia));
				return opBarbearia;
			} else {
				LOGGER.log(Level.INFO, "Alterou {0}", barbearia);
			}
		} else {
			LOGGER.log(Level.INFO, "Não alterou {0}", barbearia);
		}
		return Optional.of(barbearia);
	}

	private Optional<Barbearia> saveNew(final Barbearia barbearia, boolean refresh) {
		String sql = getInsert();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				setValuesOnStatement(barbearia, stmt);
				return stmt;
			}
		}, keyHolder);

		barbearia.setCodigo(keyHolder.getKey().intValue());

		if (row > 0) {
			if (refresh) {
				Optional<Barbearia> opBarbearia = findByCodigo(barbearia.getCodigo());
				LOGGER.log(Level.INFO, "Criou {0}", (opBarbearia.isPresent() ? opBarbearia.get() : barbearia));
				return opBarbearia;
			} else {
				LOGGER.log(Level.INFO, "Criou {0}", barbearia);
			}
		} else {
			LOGGER.log(Level.INFO, "Não criou {0}", barbearia);
		}
		return Optional.of(barbearia);
	}

	private int setValuesOnStatement(Barbearia barbearia, PreparedStatement stmt) throws SQLException {
		int idx = 1;
		stmt.setString(idx++, barbearia.getNome());
		stmt.setString(idx++, barbearia.getDescricao());
		stmt.setInt(idx++, barbearia.getCodigoEndereco());
		Time timeAssist = new Time(barbearia.getHorarioAbertura().getTime());
		stmt.setTime(idx++, timeAssist);
		timeAssist = new Time(barbearia.getHorarioFechamento().getTime());
		stmt.setTime(idx++, timeAssist);
		return idx;
	}

	protected void setValues(Barbearia b, ResultSet rs) throws SQLException {
		b.setCodigo(rs.getLong("CODIGO"));
		b.setNome(rs.getString("NOME"));
		b.setDescricao(rs.getString("DESCRICAO"));
		b.setCodigoEndereco(rs.getInt("CODIGO_ENDERECO"));
		Time timeAssist = rs.getTime("HORARIO_ABERTURA");
		b.setHorarioAbertura(new Date(timeAssist.getTime()));
		timeAssist = rs.getTime("HORARIO_FECHAMENTO");
		b.setHorarioFechamento(new Date(timeAssist.getTime()));
		b.setCadastro(rs.getDate("CADASTRO"));
		b.setAlterado(rs.getDate("ALTERADO"));
	}

	@CacheEvict(value = "barbearias", allEntries = true)
	public int delete(final Barbearia barbearia) {
		Integer result = jdbcTemplate.execute(getDelete(), new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException {
				stmt.setLong(1, barbearia.getCodigo());
				return stmt.executeUpdate();
			}
		});

		int r = (result == null ? 0 : result.intValue());
		if (r > 0) {
			LOGGER.log(Level.INFO, "Removeu {0}", barbearia);
		} else {
			LOGGER.log(Level.INFO, "Não removeu {0}", barbearia);
		}

		return r;
	}

	public Optional<Barbearia> findByCodigo(long codigo) {
		String query = getSelect() + " WHERE CODIGO=?";
		Object[] args = new Object[] { codigo };
		return find(query, args);
	}

	private Optional<Barbearia> find(String query, Object[] args) {
		List<Barbearia> result = jdbcTemplate.query(query, args, this);
		if (result == null || result.isEmpty()) {
			return Optional.empty();
		} else {
			Barbearia barbearia = (Barbearia) result.get(0);
			return Optional.ofNullable(barbearia);
		}
	}

	@Cacheable(value = "barbearias")
	public List<Barbearia> findAll() {
		return jdbcTemplate.query(getSelect(), this);
	}

	private String getSelect() {
		return "SELECT B.CODIGO, B.NOME, B.DESCRICAO, B.CODIGO_ENDERECO, B.HORARIO_ABERTURA, B.HORARIO_FECHAMENTO, B.CADASTRO, B.ALTERADO FROM BARBEARIA B";
	}

	private String getInsert() {
		return "INSERT INTO BARBEARIA (NOME, DESCRICAO, CODIGO_ENDERECO, HORARIO_ABERTURA, HORARIO_FECHAMENTO, CADASTRO, ALTERADO) VALUES (?,?,?,?,?,NOW(),NOW())";
	}

	private String getUpdate() {
		return "UPDATE BARBEARIA SET NOME=?, DESCRICAO=?, CODIGO_ENDERECO=?, HORARIO_ABERTURA=?, HORARIO_FECHAMENTO=?, ALTERADO=NOW() WHERE CODIGO=?";
	}

	private String getDelete() {
		return "DELETE FROM BARBEARIA WHERE CODIGO=?";
	}
}
