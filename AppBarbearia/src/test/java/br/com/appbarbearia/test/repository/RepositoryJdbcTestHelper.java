package br.com.appbarbearia.test.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.appbarbearia.model.Cidade;
import br.com.appbarbearia.model.Estados;
import br.com.appbarbearia.repository.CidadeRepository;

@Component
public class RepositoryJdbcTestHelper {

	JdbcTemplate jdbcTemplate;

	@Autowired
	CidadeRepository cidadeRepository;

	public RepositoryJdbcTestHelper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void limpaBancoDeDados() {
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
}
