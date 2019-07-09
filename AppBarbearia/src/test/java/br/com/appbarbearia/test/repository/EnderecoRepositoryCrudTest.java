package br.com.appbarbearia.test.repository;

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
import org.springframework.test.context.junit4.SpringRunner;

import br.com.appbarbearia.AppBarbearia.RunConfiguration;
import br.com.appbarbearia.model.Endereco;
import br.com.appbarbearia.repository.EnderecoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EnderecoRepositoryCrudTest {

	public static final int ENDERECO_CODIGO_CIDADE_1 = 1;
	public static final String ENDERECO_ENDERECO_1 = "Rua coelho Neto";
	public static final int ENDERECO_NUMERO_1 = 338;
	public static final String ENDERECO_CEP_1 = "13320-520";

	public static final int ENDERECO_CODIGO_CIDADE_2 = 2;
	public static final String ENDERECO_ENDERECO_2 = "Rua Brasil";
	public static final int ENDERECO_NUMERO_2 = 257;
	public static final String ENDERECO_CEP_2 = "13320-123";

	public static final String ENDERECO_ENDERECO_2_ALTERADO = "ALTERADO!";
	public static final int ENDERECO_NUMERO_2_ALTERADO = 9999;

	private static int codigoAdicionado1 = 0;
	private static int codigoAdicionado2 = 0;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	RepositoryJdbcTestHelper rjth;

	@Test
	public void test00_CleanUp() {
		rjth.limpaBancoDeDados();
		jdbcTemplate.execute("ALTER TABLE CIDADE AUTO_INCREMENT=0");
		rjth.criarCidade();
	}

	@Test
	public void test01_Insert1() {
		Endereco endereco = new Endereco();
		endereco.setCodigoCidade(ENDERECO_CODIGO_CIDADE_1);
		endereco.setEndereco(ENDERECO_ENDERECO_1);
		endereco.setNumero(ENDERECO_NUMERO_1);
		endereco.setCep(ENDERECO_CEP_1);

		Optional<Endereco> opEndereco = enderecoRepository.save(endereco);
		assertTrue(opEndereco.isPresent());
		codigoAdicionado1 = opEndereco.get().getCodigo();

		endereco = opEndereco.get();

		assertEquals(endereco.getCodigoCidade(), ENDERECO_CODIGO_CIDADE_1);
		assertEquals(endereco.getEndereco(), ENDERECO_ENDERECO_1);
		assertEquals(endereco.getNumero(), ENDERECO_NUMERO_1);
		assertEquals(endereco.getCep(), ENDERECO_CEP_1);
		System.out.println("test01_Insert1, Ok");
	}

	@Test
	public void test02_Insert2() {
		Endereco endereco = new Endereco();
		endereco.setCodigoCidade(ENDERECO_CODIGO_CIDADE_2);
		endereco.setEndereco(ENDERECO_ENDERECO_2);
		endereco.setNumero(ENDERECO_NUMERO_2);
		endereco.setCep(ENDERECO_CEP_2);

		Optional<Endereco> opEndereco = enderecoRepository.save(endereco);
		assertTrue(opEndereco.isPresent());
		codigoAdicionado2 = opEndereco.get().getCodigo();

		endereco = opEndereco.get();

		assertEquals(endereco.getCodigoCidade(), ENDERECO_CODIGO_CIDADE_2);
		assertEquals(endereco.getEndereco(), ENDERECO_ENDERECO_2);
		assertEquals(endereco.getNumero(), ENDERECO_NUMERO_2);
		assertEquals(endereco.getCep(), ENDERECO_CEP_2);
		System.out.println("test02_Insert2, Ok");
	}

	@Test
	public void test03_Update2() {
		Optional<Endereco> opEndereco = enderecoRepository.findByCodigo(codigoAdicionado2);
		assertTrue(opEndereco.isPresent());

		Endereco endereco = opEndereco.get();

		endereco.setEndereco(ENDERECO_ENDERECO_2_ALTERADO);
		endereco.setNumero(ENDERECO_NUMERO_2_ALTERADO);
		opEndereco = enderecoRepository.save(endereco);
		assertTrue(opEndereco.isPresent());

		endereco = opEndereco.get();
		assertEquals(endereco.getEndereco(), ENDERECO_ENDERECO_2_ALTERADO);
		assertEquals(endereco.getNumero(), ENDERECO_NUMERO_2_ALTERADO);
		System.out.println("test03_Update2, Ok");
	}

	@Test
	public void test04_List() {
		System.out.println("List . . .");
		List<Endereco> lista = enderecoRepository.findAll();
		assertEquals(lista.size(), 2);
		System.out.println("Test04_List, Ok");
	}

	@Test
	public void test05_delete1() {
		Optional<Endereco> opEndereco = enderecoRepository.findByCodigo(codigoAdicionado1);
		assertTrue(opEndereco.isPresent());
		enderecoRepository.delete(opEndereco.get());

		opEndereco = enderecoRepository.findByCodigo(codigoAdicionado1);
		assertFalse(opEndereco.isPresent());
		System.out.println("Test05_delete1, Ok");
	}
}
