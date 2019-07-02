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

import br.com.appbarbearia.model.Horario;
import br.com.appbarbearia.util.JdbcRepository;

@Repository
@Transactional
public class HorarioRepository extends JdbcRepository<Horario> implements RowMapper<Horario> {

	Logger LOGGER = Logger.getLogger(HorarioRepository.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Horario mapRow(ResultSet rs, int rowNum) throws SQLException {
		Horario h = new Horario();
		setValues(h, rs);
		return h;
	}

	public Optional<Horario> save(Horario horario) {
		return save(horario, false);
	}

	@CacheEvict(value = "horarios", allEntries = true)
	public Optional<Horario> save(final Horario horario, boolean refresh) {
		Set<ConstraintViolation<Horario>> cvs = validate(horario);
		if (cvs.isEmpty()) {
			final boolean novo = (horario.getCodigo() == 0);
			return (novo ? saveNew(horario, refresh) : saveOld(horario, refresh));
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

	private Optional<Horario> saveOld(final Horario horario, boolean refresh) {
		String sql = getUpdate();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql);
				int idx = setValuesOnStatement(horario, stmt);
				stmt.setLong(idx++, horario.getCodigo());
				return stmt;
			}
		});
		if (row > 0) {
			if (refresh) {
				Optional<Horario> opHorario = findByCodigo(horario.getCodigo());
				LOGGER.log(Level.INFO, "Alterou {0}", (opHorario.isPresent() ? opHorario.get() : horario));
				return opHorario;
			} else {
				LOGGER.log(Level.INFO, "Alterou {0}", horario);
			}
		} else {
			LOGGER.log(Level.INFO, "Não alterou {0}", horario);
		}
		return Optional.of(horario);
	}

	private Optional<Horario> saveNew(final Horario horario, boolean refresh) {
		String sql = getInsert();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				setValuesOnStatement(horario, stmt);
				return stmt;
			}
		}, keyHolder);

		horario.setCodigo(keyHolder.getKey().intValue());

		if (row > 0) {
			if (refresh) {
				Optional<Horario> opHorario = findByCodigo(horario.getCodigo());
				LOGGER.log(Level.INFO, "Criou {0}", (opHorario.isPresent() ? opHorario.get() : horario));
				return opHorario;
			} else {
				LOGGER.log(Level.INFO, "Criou {0}", horario);
			}
		} else {
			LOGGER.log(Level.INFO, "Não criou {0}", horario);
		}
		return Optional.of(horario);
	}

	private int setValuesOnStatement(Horario h, PreparedStatement stmt) throws SQLException {
		int idx = 1;
		stmt.setString(idx++, h.getDescricao());
		Time timeAssist = new Time(h.getHora().getTime());
		stmt.setTime(idx++, timeAssist);
		return idx;
	}

	protected void setValues(Horario h, ResultSet rs) throws SQLException {
		h.setCodigo(rs.getInt("CODIGO"));
		h.setDescricao(rs.getString("DESCRICAO"));
		Time timeAssist = rs.getTime("HORA");
		h.setHora(new Date(timeAssist.getTime()));
		h.setCadastro(rs.getDate("CADASTRO"));
		h.setAlterado(rs.getDate("ALTERADO"));
	}

	@CacheEvict(value = "horarios", allEntries = true)
	public int delete(final Horario horario) {
		Integer result = jdbcTemplate.execute(getDelete(), new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException {
				stmt.setLong(1, horario.getCodigo());
				return stmt.executeUpdate();
			}
		});

		int r = (result == null ? 0 : result.intValue());
		if (r > 0) {
			LOGGER.log(Level.INFO, "Removeu {0}", horario);
		} else {
			LOGGER.log(Level.INFO, "Não removeu {0}", horario);
		}

		return r;
	}

	public Optional<Horario> findByCodigo(int codigo) {
		String query = getSelect() + " WHERE CODIGO=?";
		Object[] args = new Object[] { codigo };
		return find(query, args);
	}

	private Optional<Horario> find(String query, Object[] args) {
		List<Horario> result = jdbcTemplate.query(query, args, this);
		if (result == null || result.isEmpty()) {
			return Optional.empty();
		} else {
			Horario horario = (Horario) result.get(0);
			return Optional.ofNullable(horario);
		}
	}

	@Cacheable(value = "horarios")
	public List<Horario> findAll() {
		return jdbcTemplate.query(getSelect(), this);
	}

	public String getSelect() {
		return "SELECT H.CODIGO, H.DESCRICAO, H.HORA, H.CADASTRO, H.ALTERADO FROM HORARIO H";
	}

	public String getInsert() {
		return "INSERT INTO HORARIO(DESCRICAO, HORA, CADASTRO, ALTERADO) VALUES (?,?,NOW(),NOW())";
	}

	public String getUpdate() {
		return "UPDATE HORARIO SET DESCRICAO=?, HORA=?, ALTERADO=NOW() WHERE CODIGO=?";
	}

	public String getDelete() {
		return "DELETE FROM HORARIO WHERE CODIGO=?";
	}
}
