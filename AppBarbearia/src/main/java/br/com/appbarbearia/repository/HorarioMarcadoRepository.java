package br.com.appbarbearia.repository;

import java.sql.Connection;
import java.sql.Date;
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

import br.com.appbarbearia.model.HorarioMarcado;
import br.com.appbarbearia.util.JdbcRepository;

@Transactional
@Repository
public class HorarioMarcadoRepository extends JdbcRepository<HorarioMarcado> implements RowMapper<HorarioMarcado> {

	Logger LOGGER = Logger.getLogger(HorarioMarcadoRepository.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public HorarioMarcado mapRow(ResultSet rs, int rowNum) throws SQLException {
		HorarioMarcado hm = new HorarioMarcado();
		setValues(hm, rs);
		return hm;
	}

	public Optional<HorarioMarcado> save(HorarioMarcado horarioMarcado) {
		return save(horarioMarcado, false);
	}

	@CacheEvict(value = "horariosMarcados", allEntries = true)
	public Optional<HorarioMarcado> save(final HorarioMarcado horarioMarcado, boolean refresh) {
		Set<ConstraintViolation<HorarioMarcado>> cvs = validate(horarioMarcado);
		if (cvs.isEmpty()) {
			final boolean novo = (horarioMarcado.getCodigo() == 0);
			return (novo ? saveNew(horarioMarcado, refresh) : saveOld(horarioMarcado, refresh));
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

	private Optional<HorarioMarcado> saveOld(final HorarioMarcado horarioMarcado, boolean refresh) {
		String sql = getUpdate();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql);
				int idx = setValuesOnStatement(horarioMarcado, stmt);
				stmt.setLong(idx++, horarioMarcado.getCodigo());
				return stmt;
			}
		});
		if (row > 0) {
			if (refresh) {
				Optional<HorarioMarcado> opHorarioMarcado = findByCodigo(horarioMarcado.getCodigo());
				LOGGER.log(Level.INFO, "Alterou {0}",
						(opHorarioMarcado.isPresent() ? opHorarioMarcado.get() : horarioMarcado));
				return opHorarioMarcado;
			} else {
				LOGGER.log(Level.INFO, "Alterou {0}", horarioMarcado);
			}
		} else {
			LOGGER.log(Level.INFO, "Não alterou {0}", horarioMarcado);
		}
		return Optional.of(horarioMarcado);
	}

	private Optional<HorarioMarcado> saveNew(final HorarioMarcado horarioMarcado, boolean refresh) {
		String sql = getInsert();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				setValuesOnStatement(horarioMarcado, stmt);
				return stmt;
			}
		}, keyHolder);

		horarioMarcado.setCodigo(keyHolder.getKey().intValue());

		if (row > 0) {
			if (refresh) {
				Optional<HorarioMarcado> opHorarioMarcado = findByCodigo(horarioMarcado.getCodigo());
				LOGGER.log(Level.INFO, "Criou {0}",
						(opHorarioMarcado.isPresent() ? opHorarioMarcado.get() : horarioMarcado));
				return opHorarioMarcado;
			} else {
				LOGGER.log(Level.INFO, "Criou {0}", horarioMarcado);
			}
		} else {
			LOGGER.log(Level.INFO, "Não criou {0}", horarioMarcado);
		}
		return Optional.of(horarioMarcado);
	}

	private int setValuesOnStatement(HorarioMarcado horarioMarcado, PreparedStatement stmt) throws SQLException {
		int idx = 1;
		stmt.setInt(idx++, horarioMarcado.getCodigoHorario());
		stmt.setLong(idx++, horarioMarcado.getCodigoBarbeiro());
		stmt.setLong(idx++, horarioMarcado.getCodigoCliente());
		stmt.setDate(idx++, new Date(horarioMarcado.getDia().getTime()));
		return idx;
	}

	protected void setValues(HorarioMarcado hm, ResultSet rs) throws SQLException {
		hm.setCodigo(rs.getLong("CODIGO"));
		hm.setCodigoHorario(rs.getInt("CODIGO_HORARIO"));
		hm.setCodigoBarbeiro(rs.getLong("CODIGO_BARBEIRO"));
		hm.setCodigoCliente(rs.getLong("CODIGO_CLIENTE"));
		hm.setDia(rs.getDate("DIA"));
		hm.setCadastro(rs.getDate("CADASTRO"));
		hm.setAlterado(rs.getDate("ALTERADO"));
	}

	@CacheEvict(value = "horariosMarcados", allEntries = true)
	public int delete(final HorarioMarcado horarioMarcado) {
		Integer result = jdbcTemplate.execute(getDelete(), new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException {
				stmt.setLong(1, horarioMarcado.getCodigo());
				return stmt.executeUpdate();
			}
		});

		int r = (result == null ? 0 : result.intValue());
		if (r > 0) {
			LOGGER.log(Level.INFO, "Removeu {0}", horarioMarcado);
		} else {
			LOGGER.log(Level.INFO, "Não removeu {0}", horarioMarcado);
		}

		return r;
	}

	public Optional<HorarioMarcado> findByCodigo(long codigo) {
		String query = getSelect() + " WHERE CODIGO=?";
		Object[] args = new Object[] { codigo };
		return find(query, args);
	}

	private Optional<HorarioMarcado> find(String query, Object[] args) {
		List<HorarioMarcado> result = jdbcTemplate.query(query, args, this);
		if (result == null || result.isEmpty()) {
			return Optional.empty();
		} else {
			HorarioMarcado horarioMarcado = (HorarioMarcado) result.get(0);
			return Optional.ofNullable(horarioMarcado);
		}
	}

	@Cacheable(value = "horariosMarcados")
	public List<HorarioMarcado> findAll() {
		return jdbcTemplate.query(getSelect(), this);
	}

	public String getSelect() {
		return "SELECT HM.CODIGO, HM.CODIGO_HORARIO, HM.CODIGO_BARBEIRO, HM.CODIGO_CLIENTE, HM.DIA, HM.CADASTRO, HM.ALTERADO FROM HORARIO_MARCADO HM";
	}

	public String getInsert() {
		return "INSERT INTO HORARIO_MARCADO (CODIGO_HORARIO, CODIGO_BARBEIRO, CODIGO_CLIENTE, DIA, CADASTRO, ALTERADO) VALUES (?,?,?,?,NOW(),NOW())";
	}

	public String getUpdate() {
		return "UPDATE HORARIO_MARCADO SET CODIGO_HORARIO=?, CODIGO_BARBEIRO=?, CODIGO_CLIENTE=?, DIA=?, ALTERADO=NOW() WHERE CODIGO=?";
	}

	public String getDelete() {
		return "DELETE FROM HORARIO_MARCADO WHERE CODIGO=?";
	}
}
