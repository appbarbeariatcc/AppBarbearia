package br.com.appbarbearia.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

import br.com.appbarbearia.model.Cliente;
import br.com.appbarbearia.util.JdbcRepository;

@Transactional
@Repository
public class ClienteRepository extends JdbcRepository<Cliente> implements RowMapper<Cliente> {

	Logger LOGGER = Logger.getLogger(ClienteRepository.class.getName());

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
		Cliente c = new Cliente();
		setValues(c, rs);
		return c;
	}

	public Optional<Cliente> save(Cliente cliente) {
		return save(cliente, false);
	}

	@CacheEvict(value = "clientes", allEntries = true)
	public Optional<Cliente> save(final Cliente cliente, boolean refresh) {
		Set<ConstraintViolation<Cliente>> cvs = validate(cliente);
		if (cvs.isEmpty()) {
			final boolean novo = (cliente.getCodigo() == 0);
			return (novo ? saveNew(cliente, refresh) : saveOld(cliente, refresh));
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

	private Optional<Cliente> saveOld(final Cliente cliente, boolean refresh) {
		String sql = getUpdate();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql);
				int idx = setValuesOnStatement(cliente, stmt);
				stmt.setLong(idx++, cliente.getCodigo());
				return stmt;
			}
		});
		if (row > 0) {
			if (refresh) {
				Optional<Cliente> opCliente = findByCodigo(cliente.getCodigo());
				LOGGER.log(Level.INFO, "Alterou {0}", (opCliente.isPresent() ? opCliente.get() : cliente));
				return opCliente;
			} else {
				LOGGER.log(Level.INFO, "Alterou {0}", cliente);
			}
		} else {
			LOGGER.log(Level.INFO, "Não alterou {0}", cliente);
		}
		return Optional.of(cliente);
	}

	private Optional<Cliente> saveNew(final Cliente cliente, boolean refresh) {
		String sql = getInsert();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int row = this.jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				setValuesOnStatement(cliente, stmt);
				return stmt;
			}
		}, keyHolder);

		cliente.setCodigo(keyHolder.getKey().longValue());

		if (row > 0) {
			if (refresh) {
				Optional<Cliente> opCliente = findByCodigo(cliente.getCodigo());
				LOGGER.log(Level.INFO, "Criou {0}", (opCliente.isPresent() ? opCliente.get() : cliente));
				return opCliente;
			} else {
				LOGGER.log(Level.INFO, "Criou {0}", cliente);
			}
		} else {
			LOGGER.log(Level.INFO, "Não criou {0}", cliente);
		}
		return Optional.of(cliente);
	}

	private int setValuesOnStatement(Cliente c, PreparedStatement stmt) throws SQLException {
		int idx = 1;
		stmt.setInt(idx++, c.getCodigoCidade());
		stmt.setString(idx++, c.getNome());
		stmt.setString(idx++, c.getRg());
		stmt.setString(idx++, c.getCpf());
		stmt.setInt(idx++, c.getTelefone());
		stmt.setInt(idx++, c.getCelular());
		setNullSafe(stmt, c.getFoto(), idx++);
		stmt.setTimestamp(idx++, new Timestamp(c.getDataNascimento().getTime()));
		return idx;
	}

	protected void setValues(Cliente c, ResultSet rs) throws SQLException {
		c.setCodigo(rs.getLong("CODIGO"));
		c.setCodigoCidade(rs.getInt("CODIGO_CIDADE"));
		c.setNome(rs.getString("NOME"));
		c.setRg(rs.getString("RG"));
		c.setCpf(rs.getString("CPF"));
		c.setTelefone(rs.getInt("TELEFONE"));
		c.setCelular(rs.getInt("CELULAR"));
		c.setFoto(rs.getString("FOTO"));
		c.setDataNascimento(rs.getDate("DATA_NASCIMENTO"));
		c.setCadastro(rs.getDate("CADASTRO"));
		c.setAlterado(rs.getDate("ALTERADO"));
	}

	@CacheEvict(value = "clientes", allEntries = true)
	public int delete(final Cliente cliente) {
		Integer result = jdbcTemplate.execute(getDelete(), new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement stmt) throws SQLException {
				stmt.setLong(1, cliente.getCodigo());
				return stmt.executeUpdate();
			}
		});
		int r = (result == null ? 0 : result.intValue());
		if (r > 0) {
			LOGGER.log(Level.INFO, "Removeu {0}", cliente);
		} else {
			LOGGER.log(Level.INFO, "Não removeu {0}", cliente);
		}

		return r;
	}

	public Optional<Cliente> findByCodigo(long l) {
		String query = getSelect() + " WHERE CODIGO=?";
		Object[] args = new Object[] { l };
		return find(query, args);
	}

	private Optional<Cliente> find(String query, Object[] args) {
		List<Cliente> result = jdbcTemplate.query(query, args, this);
		if (result == null || result.isEmpty()) {
			return Optional.empty();
		} else {
			Cliente cliente = (Cliente) result.get(0);
			return Optional.ofNullable(cliente);
		}
	}

	@Cacheable(value = "clientes")
	public List<Cliente> findAll() {
		return jdbcTemplate.query(getSelect(), this);
	}

	private String getSelect() {
		return "SELECT C.CODIGO, C.CODIGO_CIDADE, C.NOME, C.RG, C.CPF, C.TELEFONE, C.CELULAR, C.FOTO, C.DATA_NASCIMENTO, C.CADASTRO, C.ALTERADO FROM CLIENTE C";
	}

	private String getInsert() {
		return "INSERT INTO CLIENTE (CODIGO_CIDADE, NOME, RG, CPF, TELEFONE, CELULAR, FOTO, DATA_NASCIMENTO, CADASTRO, ALTERADO) VALUES (?,?,?,?,?,?,?,?,NOW(),NOW())";
	}

	private String getUpdate() {
		return "UPDATE CLIENTE SET NOME=?, RG=?, CPF=?, TELEFONE=?, CELULAR=?, FOTO=?, DATA_NASCIMENTO=?, ALTERADO=NOW() WHERE CODIGO = ?";
	}

	private String getDelete() {
		return "DELETE FROM CLIENTE WHERE CODIGO = ?";
	}
}
