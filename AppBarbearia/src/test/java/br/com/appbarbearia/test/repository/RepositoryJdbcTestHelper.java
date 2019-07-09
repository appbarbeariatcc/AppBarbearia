package br.com.appbarbearia.test.repository;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.appbarbearia.model.Barbeiro;
import br.com.appbarbearia.model.Cidade;
import br.com.appbarbearia.model.Cliente;
import br.com.appbarbearia.model.Endereco;
import br.com.appbarbearia.model.Estados;
import br.com.appbarbearia.model.Horario;
import br.com.appbarbearia.repository.BarbeiroRepository;
import br.com.appbarbearia.repository.CidadeRepository;
import br.com.appbarbearia.repository.ClienteRepository;
import br.com.appbarbearia.repository.EnderecoRepository;
import br.com.appbarbearia.repository.HorarioRepository;

@Component
public class RepositoryJdbcTestHelper {

	JdbcTemplate jdbcTemplate;

	@Autowired
	BarbeiroRepository barbeiroRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	HorarioRepository horarioRepository;

	@Autowired
	ClienteRepository clienteRepository;

	public RepositoryJdbcTestHelper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void limpaBancoDeDados() {
		jdbcTemplate.execute("DELETE FROM HORARIO_MARCADO");
		jdbcTemplate.execute("ALTER TABLE HORARIO_MARCADO AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM CLIENTE");
		jdbcTemplate.execute("ALTER TABLE CLIENTE AUTO_INCREMENT = 0");
		// jdbcTemplate.execute("DELETE FROM PROMOCAO");
		// jdbcTemplate.execute("ALTER TABLE PROMOCAO AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM SERVICO");
		jdbcTemplate.execute("ALTER TABLE SERVICO AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM BARBEARIA");
		jdbcTemplate.execute("ALTER TABLE BARBEARIA AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM BARBEIRO");
		jdbcTemplate.execute("ALTER TABLE BARBEIRO AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM ENDERECO");
		jdbcTemplate.execute("ALTER TABLE ENDERECO AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM CIDADE");
		jdbcTemplate.execute("ALTER TABLE CIDADE AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM HORARIO");
		jdbcTemplate.execute("ALTER TABLE HORARIO AUTO_INCREMENT = 0");
	}

	public void criarCidade() {
		String[][] cidadeData = new String[][] { { "Salto", "SP" }, { "Indaiatuba", "SP" } };

		for (String[] cidadeInfo : cidadeData) {
			Cidade cidade = new Cidade();
			int idx = 0;
			cidade.setNome(cidadeInfo[idx++]);
			cidade.setEstado(Estados.valueOf(cidadeInfo[idx++]));
			cidadeRepository.save(cidade);
		}
	}

	public void criarEndereco() {
		criarCidade();

		String[][] enderecoData = new String[][] { { "1", "Rua Coelho Neto", "338", "13320-520" },
				{ "1", "Rua Brasil", "257", "13320-500" } };
		for (String[] enderecoInfo : enderecoData) {
			int idx = 0;
			Endereco endereco = new Endereco();
			endereco.setCodigoCidade(Integer.parseInt(enderecoInfo[idx++]));
			endereco.setEndereco(enderecoInfo[idx++]);
			endereco.setNumero(Integer.parseInt(enderecoInfo[idx++]));
			endereco.setCep(enderecoInfo[idx++]);
			enderecoRepository.save(endereco);
		}
	}

	public void criarBarbeiro() {
		criarCidade();

		String[][] barbeiroData = new String[][] {
				{ "1", "LUGOR", "50.037.334-6", "433044988/93", "46021192", "972932872" },
				{ "1", "LUIS", "11.111.111-6", "222222222/43", "46021192", "943026511" } };
		for (String[] barbeiroInfo : barbeiroData) {
			int idx = 0;
			Calendar calendar = Calendar.getInstance();
			Barbeiro barbeiro = new Barbeiro();
			barbeiro.setCodigoCidade(Integer.parseInt(barbeiroInfo[idx++]));
			barbeiro.setNome(barbeiroInfo[idx++]);
			barbeiro.setRg(barbeiroInfo[idx++]);
			barbeiro.setCpf(barbeiroInfo[idx++]);
			barbeiro.setTelefone(Integer.parseInt(barbeiroInfo[idx++]));
			barbeiro.setCelular(Integer.parseInt(barbeiroInfo[idx++]));
			if(barbeiro.getNome().contains("LUCAS")){
				calendar.set(1998, 06, 20);
				barbeiro.setDataNascimento(calendar.getTime());
			} else {
				calendar.set(1969, 10, 1);
				barbeiro.setDataNascimento(calendar.getTime());
			}
			barbeiroRepository.save(barbeiro);
		}
	}

	public void criarHorario() {
		String[][] horarioData = new String[][] { { "UMA HORA E 15 MINUTOS" }, { "DUAS HORAS E 15 MINUTOS" } };

		for (String[] horarioInfo : horarioData) {
			Horario horario = new Horario();
			Calendar calendar = Calendar.getInstance();
			int idx = 0;
			horario.setDescricao(horarioInfo[idx++]);
			if (horario.getDescricao().contains("UMA HORA E 15 MINUTOS")) {
				calendar.set(Calendar.HOUR_OF_DAY, 1);
				calendar.set(Calendar.MINUTE, 15);
				calendar.set(Calendar.SECOND, 0);
				horario.setHora(calendar.getTime());
			} else {
				calendar.set(Calendar.HOUR_OF_DAY, 2);
				calendar.set(Calendar.MINUTE, 15);
				calendar.set(Calendar.SECOND, 0);
				horario.setHora(calendar.getTime());
			}
			horarioRepository.save(horario);
		}
	}

	public void criarCliente() {
		String[][] clienteData = new String[][] {
				{ "1", "LUGOR", "50.037.334-6", "433044988/93", "46021192", "972932872" },
				{ "1", "LUIS", "11.111.111-6", "222222222/43", "46021192", "943026511" } };
		for (String[] clienteInfo : clienteData) {
			Calendar calendar = Calendar.getInstance();
			int idx = 0;
			Cliente cliente = new Cliente();
			cliente.setCodigoCidade(Integer.parseInt(clienteInfo[idx++]));
			cliente.setNome(clienteInfo[idx++]);
			cliente.setRg(clienteInfo[idx++]);
			cliente.setCpf(clienteInfo[idx++]);
			cliente.setTelefone(Integer.parseInt(clienteInfo[idx++]));
			cliente.setCelular(Integer.parseInt(clienteInfo[idx++]));
			if (cliente.getNome().contains("LUCAS")) {
				calendar.set(1997, 06, 20);
				cliente.setDataNascimento(calendar.getTime());
			} else {
				calendar.set(1970, 06, 20);
				cliente.setDataNascimento(calendar.getTime());
			}
			clienteRepository.save(cliente);
		}
	}
}
