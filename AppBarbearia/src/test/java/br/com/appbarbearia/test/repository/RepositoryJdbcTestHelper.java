package br.com.appbarbearia.test.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.appbarbearia.model.Cidade;
import br.com.appbarbearia.model.Endereco;
import br.com.appbarbearia.model.Estados;
import br.com.appbarbearia.repository.*;

@Component
public class RepositoryJdbcTestHelper {

	JdbcTemplate jdbcTemplate;

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	EnderecoRepository enderecoRepository;
	
	public RepositoryJdbcTestHelper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void limpaBancoDeDados() {
		jdbcTemplate.execute("DELETE FROM BARBEARIA");
		jdbcTemplate.execute("ALTER TABLE BARBEARIA AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM ENDERECO");
		jdbcTemplate.execute("ALTER TABLE ENDERECO AUTO_INCREMENT = 0");
		jdbcTemplate.execute("DELETE FROM CIDADE");
		jdbcTemplate.execute("ALTER TABLE CIDADE AUTO_INCREMENT = 0");
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
		
		String[][] enderecoData = new String[][] {
			{"1", "Rua Coelho Neto", "338", "13320-520"},
			{"1", "Rua Brasil", "257", "13320-500"}
		};
		for (String[] enderecoInfo: enderecoData) {
			int idx = 0;
			Endereco endereco = new Endereco();
			endereco.setCodigoCidade(Integer.parseInt(enderecoInfo[idx++]));
			endereco.setEndereco(enderecoInfo[idx++]);
			endereco.setNumero(Integer.parseInt(enderecoInfo[idx++]));
			endereco.setCep(enderecoInfo[idx++]);
			enderecoRepository.save(endereco);
		}
	}
}
