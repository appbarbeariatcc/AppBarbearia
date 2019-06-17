package br.com.appbarbearia.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.appbarbearia.model.Cidade;
import br.com.appbarbearia.model.Estados;
import br.com.appbarbearia.repository.CidadeRepository;
import br.com.appbarbearia.runconfig.RunConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RunConfiguration.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CidadeRepositoryCrudTest {

	public static final String CIDADE_NOME_1 = "Salto";
	public static final Estados CIDADE_ESTADO_1 = Estados.SP;

	public static final String CIDADE_NOME_2 = "Itu";
	public static final Estados CIDADE_ESTADO_2 = Estados.SP;

	public static final String CIDADE_NOME_2_ALTERADO = "Indaiatuba";

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	CidadeRepository cidadeRepository;

	static int codigoAdicionado1 = 0;
	static int codigoAdicionado2 = 0;

	@Test
	public void test00_CleanUp() {
		jdbcTemplate.execute("DELETE FROM CIDADE");
		jdbcTemplate.execute("ALTER TABLE CIDADE AUTO_INCREMENT = 0");
	}

	@Test
	public void test01_Insert1() {
		Cidade cidade = new Cidade();
		cidade.setNome(CIDADE_NOME_1);
		cidade.setEstado(CIDADE_ESTADO_1);
		Optional<Cidade> opCidade = cidadeRepository.save(cidade);

		assertTrue(opCidade.isPresent());

		cidade = opCidade.get();
		assertEquals(cidade.getNome(), CIDADE_NOME_1);
		assertEquals(cidade.getEstado(), CIDADE_ESTADO_1);
		codigoAdicionado1 = opCidade.get().getCodigo();
		System.out.println("test01_Insert1, Ok");
	}

	@Test
	public void test02_Insert2() {
		Cidade cidade = new Cidade();
		cidade.setNome(CIDADE_NOME_2);
		cidade.setEstado(CIDADE_ESTADO_2);
		Optional<Cidade> opCidade = cidadeRepository.save(cidade);

		assertTrue(opCidade.isPresent());

		cidade = opCidade.get();
		assertEquals(cidade.getNome(), CIDADE_NOME_2);
		assertEquals(cidade.getEstado(), CIDADE_ESTADO_2);
		codigoAdicionado2 = opCidade.get().getCodigo();
		System.out.println("test02_Insert2, Ok");
	}

	@Test
	public void test03_Update2() {
		Optional<Cidade> opCidade = cidadeRepository.findByCodigo(codigoAdicionado2);
		assertTrue(opCidade.isPresent());
		
		Cidade cidade = opCidade.get();
		cidade.setNome(CIDADE_NOME_2_ALTERADO);
		opCidade = cidadeRepository.save(cidade);
		assertTrue(opCidade.isPresent());
		
		cidade = opCidade.get();
		
		assertEquals(cidade.getNome(), CIDADE_NOME_2_ALTERADO);
		System.out.println("test03_Update2, Ok");
	}

	@Test
	public void test04_List() {
		System.out.println("List . . .");
		List<Cidade> lista = cidadeRepository.findAll();
		assertEquals(lista.size(), 2);
		System.out.println("Test04_List, Ok");
	}

	@Test
	public void test05_delete1() {
		Optional<Cidade> opCidade = cidadeRepository.findByCodigo(codigoAdicionado1);
		assertTrue(opCidade.isPresent());
		cidadeRepository.delete(opCidade.get());
		
		opCidade = cidadeRepository.findByCodigo(codigoAdicionado1);
		assertFalse(opCidade.isPresent());
		System.out.println("Test05_delete1, Ok");
	}
}