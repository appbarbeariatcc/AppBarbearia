package br.com.appbarbearia.test.repository;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.appbarbearia.model.Barbeiro;
import br.com.appbarbearia.model.Cidade;
import br.com.appbarbearia.model.Endereco;
import br.com.appbarbearia.model.Estados;
import br.com.appbarbearia.model.Horario;
import br.com.appbarbearia.repository.BarbeiroRepository;
import br.com.appbarbearia.repository.CidadeRepository;
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
	HorarioRepository horarioRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	public RepositoryJdbcTestHelper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void limpaBancoDeDados() {
		jdbcTemplate.execute("DELETE FROM BARBEARIA");
		jdbcTemplate.execute("ALTER TABLE BARBEARIA AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM HORARIO_MARCADO");
		jdbcTemplate.execute("ALTER TABLE HORARIO_MARCADO AUTO_INCREMENT = 0");
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
			Barbeiro barbeiro = new Barbeiro();
			barbeiro.setCodigoCidade(Integer.parseInt(barbeiroInfo[idx++]));
			barbeiro.setNome(barbeiroInfo[idx++]);
			barbeiro.setRg(barbeiroInfo[idx++]);
			barbeiro.setCpf(barbeiroInfo[idx++]);
			barbeiro.setTelefone(Integer.parseInt(barbeiroInfo[idx++]));
			barbeiro.setCelular(Integer.parseInt(barbeiroInfo[idx++]));
			barbeiroRepository.save(barbeiro);
		}
	}

	public void criarHorario() {
		String[][] horarioData = new String[][] { { "UMA HORA E QUINZE MINUTOS" }, { "DUAS HORAS E QUINZE MINUTOS" } };
		for (String[] horarioInfo : horarioData) {
			int idx = 0;
			Horario horario = new Horario();
			Calendar calendar = Calendar.getInstance();
			horario.setDescricao(horarioInfo[idx++]);
			if (horario.getDescricao().contains("UMA HORA")) {
				calendar.set(Calendar.HOUR_OF_DAY, 1);
				calendar.set(Calendar.MINUTE, 15);
				calendar.set(Calendar.SECOND, 0);
			} else {
				calendar.set(Calendar.HOUR_OF_DAY, 2);
				calendar.set(Calendar.MINUTE, 15);
				calendar.set(Calendar.SECOND, 0);
			}
			horario.setHora(calendar.getTime());
			horarioRepository.save(horario);
		}
	}
}
