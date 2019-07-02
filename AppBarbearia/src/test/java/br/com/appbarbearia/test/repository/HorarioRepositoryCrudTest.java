package br.com.appbarbearia.test.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.appbarbearia.AppBarbearia.RunConfiguration;
import br.com.appbarbearia.model.Horario;
import br.com.appbarbearia.repository.HorarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HorarioRepositoryCrudTest {

	public static String HORARIO_DESCRICAO_1 = "UMA HORA E QUINZE MINUTOS";

	public static String HORARIO_DESCRICAO_2 = "DUAS HORAS E QUINZE MINUTOS";

	public static String HORARIO_DESCRICAO_2_ALTERADO = "2 HORAS E QUINZE MINUTOS";

	@Autowired
	HorarioRepository horarioRepository;

	@Autowired
	RepositoryJdbcTestHelper rjth;

	public static int codigoAdicionado1 = 0;
	public static int codigoAdicionado2 = 0;

	@Test
	public void test00_CleanUp() {
		rjth.limpaBancoDeDados();
	}

	@Test
	public void test01_Insert1() {
		Horario h = new Horario();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 15);
		calendar.set(Calendar.SECOND, 0);
		h.setDescricao(HORARIO_DESCRICAO_1);
		h.setHora(calendar.getTime());

		Optional<Horario> opHorario = horarioRepository.save(h);

		assertTrue(opHorario.isPresent());
		h = opHorario.get();
		assertEquals(h.getDescricao(), HORARIO_DESCRICAO_1);
		codigoAdicionado1 = opHorario.get().getCodigo();
		System.out.println("Test01_Insert1, Ok");
	}

	@Test
	public void test02_Insert2() {
		Horario h = new Horario();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 2);
		calendar.set(Calendar.MINUTE, 15);
		calendar.set(Calendar.SECOND, 0);
		h.setDescricao(HORARIO_DESCRICAO_2);
		h.setHora(calendar.getTime());

		Optional<Horario> opHorario = horarioRepository.save(h);

		assertTrue(opHorario.isPresent());
		h = opHorario.get();
		assertEquals(h.getDescricao(), HORARIO_DESCRICAO_2);
		codigoAdicionado2 = opHorario.get().getCodigo();
		System.out.println("Test02_Insert2, Ok");
	}

	@Test
	public void test03_Update2() {
		Optional<Horario> opHorario = horarioRepository.findByCodigo(codigoAdicionado2);
		assertTrue(opHorario.isPresent());
		Horario h = opHorario.get();
		h.setDescricao(HORARIO_DESCRICAO_2_ALTERADO);

		opHorario = horarioRepository.save(h);
		assertTrue(opHorario.isPresent());
		h = opHorario.get();
		assertEquals(h.getDescricao(), HORARIO_DESCRICAO_2_ALTERADO);
		System.out.println("test03_Update2, Ok");
	}

	@Test
	public void test04_List() {
		System.out.println("List . . .");
		List<Horario> lista = horarioRepository.findAll();
		assertEquals(lista.size(), 2);
		System.out.println("Test04_List, Ok");
	}

	@Test
	public void test05_delete1() {
		Optional<Horario> opHorario = horarioRepository.findByCodigo(codigoAdicionado1);
		assertTrue(opHorario.isPresent());
		horarioRepository.delete(opHorario.get());

		opHorario = horarioRepository.findByCodigo(codigoAdicionado1);
		assertFalse(opHorario.isPresent());
		System.out.println("Test05_delete1, Ok");
	}
}
