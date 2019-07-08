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
import br.com.appbarbearia.model.Barbeiro;
import br.com.appbarbearia.repository.BarbeiroRepository;
import br.com.appbarbearia.repository.CidadeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarbeiroRepositoryCrudTest {

	public static final long BARBEIRO_CODIGO_BARBEARIA_1 = 1;
	public static final int BARBEIRO_CODIGO_CIDADE_1 = 1;
	public static final String BARBEIRO_NOME_1 = "LUGOR";
	public static final String BARBEIRO_RG_1 = "50.037.334-6";
	public static final String BARBEIRO_CPF_1 = "433044988/93";
	public static final Integer BARBEIRO_TELEFONE_1 = 46021192;
	public static final int BARBEIRO_CELULAR_1 = 972932872;

	public static final long BARBEIRO_CODIGO_BARBEARIA_2 = 2;
	public static final int BARBEIRO_CODIGO_CIDADE_2 = 1;
	public static final String BARBEIRO_NOME_2 = "Luis";
	public static final String BARBEIRO_RG_2 = "111.111.111-11";
	public static final String BARBEIRO_CPF_2 = "730132543/42";
	public static final Integer BARBEIRO_TELEFONE_2 = 46021192;
	public static final int BARBEIRO_CELULAR_2 = 984201142;

	public static final String BARBEIRO_NOME_2_ALTERADO = "Luis";
	public static final Integer BARBEIRO_TELEFONE_2_ALTERADO = 46021192;
	public static final int BARBEIRO_CELULAR_2_ALTERADO = 984201142;

	static long codigoAdicionado1 = 0;
	static long codigoAdicionado2 = 0;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	BarbeiroRepository barbeiroRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	RepositoryJdbcTestHelper rjth;

	@Test
	public void test00_CleanUp() {
		rjth.limpaBancoDeDados();
		rjth.criarCidade();
	}

	@Test
	public void test01_Insert1() {
		Barbeiro barbeiro = new Barbeiro();
		barbeiro.setCodigoBarbearia(BARBEIRO_CODIGO_BARBEARIA_1);
		barbeiro.setCodigoCidade(BARBEIRO_CODIGO_CIDADE_1);
		barbeiro.setNome(BARBEIRO_NOME_1);
		barbeiro.setRg(BARBEIRO_RG_1);
		barbeiro.setCpf(BARBEIRO_CPF_1);
		barbeiro.setTelefone(BARBEIRO_TELEFONE_1);
		barbeiro.setCelular(BARBEIRO_CELULAR_1);

		Optional<Barbeiro> opBarbeiro = barbeiroRepository.save(barbeiro);
		assertTrue(opBarbeiro.isPresent());

		barbeiro = opBarbeiro.get();

		assertEquals(barbeiro.getCodigoBarbearia(), BARBEIRO_CODIGO_BARBEARIA_1);
		assertEquals(barbeiro.getCodigoCidade(), BARBEIRO_CODIGO_CIDADE_1);
		assertEquals(barbeiro.getNome(), BARBEIRO_NOME_1);
		assertEquals(barbeiro.getRg(), BARBEIRO_RG_1);
		assertEquals(barbeiro.getCpf(), BARBEIRO_CPF_1);
		assertEquals(barbeiro.getTelefone(), BARBEIRO_TELEFONE_1);
		assertEquals(barbeiro.getCelular(), BARBEIRO_CELULAR_1);
		codigoAdicionado1 = opBarbeiro.get().getCodigo();
		System.out.println("test01_Insert1, Ok");
	}

	@Test
	public void test02_Insert1() {
		Barbeiro barbeiro = new Barbeiro();
		barbeiro.setCodigoBarbearia(BARBEIRO_CODIGO_BARBEARIA_2);
		barbeiro.setCodigoCidade(BARBEIRO_CODIGO_CIDADE_2);
		barbeiro.setNome(BARBEIRO_NOME_2);
		barbeiro.setRg(BARBEIRO_RG_2);
		barbeiro.setCpf(BARBEIRO_CPF_2);
		barbeiro.setTelefone(BARBEIRO_TELEFONE_2);
		barbeiro.setCelular(BARBEIRO_CELULAR_2);

		Optional<Barbeiro> opBarbeiro = barbeiroRepository.save(barbeiro);
		assertTrue(opBarbeiro.isPresent());

		barbeiro = opBarbeiro.get();

		assertEquals(barbeiro.getCodigoBarbearia(), BARBEIRO_CODIGO_BARBEARIA_2);
		assertEquals(barbeiro.getCodigoCidade(), BARBEIRO_CODIGO_CIDADE_2);
		assertEquals(barbeiro.getNome(), BARBEIRO_NOME_2);
		assertEquals(barbeiro.getRg(), BARBEIRO_RG_2);
		assertEquals(barbeiro.getCpf(), BARBEIRO_CPF_2);
		assertEquals(barbeiro.getTelefone(), BARBEIRO_TELEFONE_2);
		assertEquals(barbeiro.getCelular(), BARBEIRO_CELULAR_2);
		codigoAdicionado2 = opBarbeiro.get().getCodigo();
		System.out.println("test02_Insert2, Ok");
	}

	@Test
	public void test03_Update2() {
		Optional<Barbeiro> opBarbeiro = barbeiroRepository.findByCodigo(codigoAdicionado2);
		assertTrue(opBarbeiro.isPresent());

		Barbeiro barbeiro = opBarbeiro.get();
		barbeiro.setNome(BARBEIRO_NOME_2_ALTERADO);
		barbeiro.setTelefone(BARBEIRO_TELEFONE_2_ALTERADO);
		barbeiro.setCelular(BARBEIRO_CELULAR_2_ALTERADO);
		assertTrue(opBarbeiro.isPresent());

		barbeiro = opBarbeiro.get();
		assertEquals(barbeiro.getNome(), BARBEIRO_NOME_2_ALTERADO);
		assertEquals(barbeiro.getTelefone(), BARBEIRO_TELEFONE_2_ALTERADO);
		assertEquals(barbeiro.getCelular(), BARBEIRO_CELULAR_2_ALTERADO);
		System.out.println("test03_Update2, Ok");
	}

	@Test
	public void test04_List() {
		System.out.println("List . . .");
		List<Barbeiro> lista = barbeiroRepository.findAll();
		assertEquals(lista.size(), 2);
		System.out.println("Test04_List, Ok");
	}

	@Test
	public void test05_delete01() {
		Optional<Barbeiro> opBarbeiro = barbeiroRepository.findByCodigo(codigoAdicionado1);
		assertTrue(opBarbeiro.isPresent());
		barbeiroRepository.delete(opBarbeiro.get());

		opBarbeiro = barbeiroRepository.findByCodigo(codigoAdicionado1);
		assertFalse(opBarbeiro.isPresent());
		System.out.println("Test05_delete1, Ok");
	}

}
