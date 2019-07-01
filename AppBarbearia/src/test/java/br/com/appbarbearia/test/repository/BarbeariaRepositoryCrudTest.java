package br.com.appbarbearia.test.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.TimeZoneEditor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.appbarbearia.AppBarbearia.RunConfiguration;
import br.com.appbarbearia.model.Barbearia;
import br.com.appbarbearia.repository.BarbeariaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarbeariaRepositoryCrudTest{

//	TODO VER PARA TROCAR HORARIO DE ABERTURA E FECHAMENTO POR CODIGO DOS HORARIOS - AI NO SELECT USA IN() OU BETWEEN
//	TODO CAMPOS TIME ESTÃO INSERINDO VALORES ERRADOS
//  TODO FAZER O CriarBarbearia() no RepositoryJdbcTestHelper
	
	public static final String BARBEARIA_NOME_1 = "Barbearia do Juquinha";
	public static final String BARBEARIA_DESCRICAO_1 = "A melhor barbearia da região... BY FAR";
	public static final int BARBEARIA_CODIGO_ENDERECO_1 = 1;

	public static final String BARBEARIA_NOME_2 = "Barbearia do Zezinho";
	public static final String BARBEARIA_DESCRICAO_2 = "Melhor do que a do Juquinha... BY THE WAY";
	public static final int BARBEARIA_CODIGO_ENDERECO_2 = 2;
	
	public static final String BARBEARIA_NOME_2_ALTERADO = "Barbearia do zezin";
	
	private static long codigoAdicionado1 = 0;
	private static long codigoAdicionado2 = 0;
	
	@Autowired
	BarbeariaRepository barbeariaRepository;
	
	@Autowired
	RepositoryJdbcTestHelper rjth;
	
	@Test
	public void test00_CleanUp() {
		rjth.limpaBancoDeDados();
		rjth.criarEndereco();
	}

	@Test
	public void test01_Insert1() {
		Barbearia b = new Barbearia();
		Calendar calendar = Calendar.getInstance();
		b.setNome(BARBEARIA_NOME_1);
		b.setDescricao(BARBEARIA_DESCRICAO_1);
		b.setCodigoEndereco(BARBEARIA_CODIGO_ENDERECO_1);
//		calendar.setTimeZone(TimeZone.getTimeZone("Brazil/East"));
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		b.setHorarioAbertura(calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 18);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		b.setHorarioFechamento(calendar.getTime());
		Optional<Barbearia> opBarbearia = barbeariaRepository.save(b);
		assertTrue(opBarbearia.isPresent());
		
		b = opBarbearia.get();
		assertEquals(b.getNome(), BARBEARIA_NOME_1);
		assertEquals(b.getDescricao(), BARBEARIA_DESCRICAO_1);
		assertEquals(b.getCodigoEndereco(), BARBEARIA_CODIGO_ENDERECO_1);
		codigoAdicionado1 = opBarbearia.get().getCodigo();
		System.out.println("test01_Insert1, Ok");
	}
	
	@Test
	public void test02_Insert2() {
		Barbearia b = new Barbearia();
		Calendar calendar = Calendar.getInstance();
		b.setNome(BARBEARIA_NOME_2);
		b.setDescricao(BARBEARIA_DESCRICAO_2);
		b.setCodigoEndereco(BARBEARIA_CODIGO_ENDERECO_2);
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		b.setHorarioAbertura(calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 18);
		calendar.set(Calendar.MINUTE, 0);
		b.setHorarioFechamento(calendar.getTime());
		Optional<Barbearia> opBarbearia = barbeariaRepository.save(b);
		
		assertTrue(opBarbearia.isPresent());
		
		b = opBarbearia.get();
		assertEquals(b.getNome(), BARBEARIA_NOME_2);
		assertEquals(b.getDescricao(), BARBEARIA_DESCRICAO_2);
		assertEquals(b.getCodigoEndereco(), BARBEARIA_CODIGO_ENDERECO_2);
		codigoAdicionado2 = opBarbearia.get().getCodigo();
		System.out.println("test02_Insert2, Ok");
	}
	
	@Test
	public void test03_Update2() {
		Optional<Barbearia> opBarbearia = barbeariaRepository.findByCodigo(codigoAdicionado2);
		assertTrue(opBarbearia.isPresent());
		Barbearia b = opBarbearia.get();
		b.setNome(BARBEARIA_NOME_2_ALTERADO);
		opBarbearia = barbeariaRepository.save(b);
		assertTrue(opBarbearia.isPresent());
		b = opBarbearia.get();
		assertEquals(b.getNome(), BARBEARIA_NOME_2_ALTERADO);
		System.out.println("test03_Update3, Ok");
	}
	
	@Test
	public void test04_List() {
		System.out.println("List . . .");
		List<Barbearia> lista = barbeariaRepository.findAll();
		assertEquals(lista.size(), 2);
		System.out.println("Test04_List, Ok");
	}

//	@Test
	public void test05_delete1() {
		Optional<Barbearia> opBarbearia = barbeariaRepository.findByCodigo(codigoAdicionado1);
		assertTrue(opBarbearia.isPresent());
		barbeariaRepository.delete(opBarbearia.get());
		
		opBarbearia = barbeariaRepository.findByCodigo(codigoAdicionado1);
		assertFalse(opBarbearia.isPresent());
		System.out.println("Test05_delete1, Ok");
	}
}
