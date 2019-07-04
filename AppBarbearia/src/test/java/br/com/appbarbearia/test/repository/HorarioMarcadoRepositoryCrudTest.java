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
import br.com.appbarbearia.model.HorarioMarcado;
import br.com.appbarbearia.repository.HorarioMarcadoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HorarioMarcadoRepositoryCrudTest {

	public static final int HORARIO_MARCADO_CODIGO_HORARIO_1 = 1;
	public static final long HORARIO_MARCADO_CODIGO_BARBEIRO_1 = 1;
	public static final long HORARIO_MARCADO_CODIGO_CLIENTE_1 = 1;

	public static final int HORARIO_MARCADO_CODIGO_HORARIO_2 = 2;
	public static final long HORARIO_MARCADO_CODIGO_BARBEIRO_2 = 1;
	public static final long HORARIO_MARCADO_CODIGO_CLIENTE_2 = 2;

	public static final int HORARIO_MARCADO_CODIGO_BARBEIRO_2_ALTERADO = 2;

	private static long codigoAdicionado1 = 0;
	private static long codigoAdicionado2 = 0;

	@Autowired
	HorarioMarcadoRepository horarioMarcadoRepository;

	@Autowired
	RepositoryJdbcTestHelper rjth;

	@Test
	public void test00_CleanUp() {
		rjth.limpaBancoDeDados();
		rjth.criarBarbeiro();
		rjth.criarHorario();
//		rjth.criarCliente();
	}

	@Test
	public void test01_Insert1() {
		HorarioMarcado hm = new HorarioMarcado();
		hm.setCodigoHorario(HORARIO_MARCADO_CODIGO_HORARIO_1);
		hm.setCodigoBarbeiro(HORARIO_MARCADO_CODIGO_BARBEIRO_1);
		hm.setCodigoCliente(HORARIO_MARCADO_CODIGO_CLIENTE_1);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2019, 07, 03, 0, 0, 0);
		hm.setDia(calendar.getTime());
		Optional<HorarioMarcado> opHorarioMarcado = horarioMarcadoRepository.save(hm);
		assertTrue(opHorarioMarcado.isPresent());
		hm = opHorarioMarcado.get();

		assertEquals(hm.getCodigoHorario(), HORARIO_MARCADO_CODIGO_HORARIO_1);
		assertEquals(hm.getCodigoBarbeiro(), HORARIO_MARCADO_CODIGO_BARBEIRO_1);
		assertEquals(hm.getCodigoCliente(), HORARIO_MARCADO_CODIGO_CLIENTE_1);
		codigoAdicionado1 = opHorarioMarcado.get().getCodigo();
		System.out.println("test01_Insert1, OK");
	}

	@Test
	public void test02_Insert2() {
		HorarioMarcado hm = new HorarioMarcado();
		hm.setCodigoHorario(HORARIO_MARCADO_CODIGO_HORARIO_2);
		hm.setCodigoBarbeiro(HORARIO_MARCADO_CODIGO_BARBEIRO_2);
		hm.setCodigoCliente(HORARIO_MARCADO_CODIGO_CLIENTE_2);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2019, 07, 03, 0, 0, 0);
		hm.setDia(calendar.getTime());
		Optional<HorarioMarcado> opHorarioMarcado = horarioMarcadoRepository.save(hm);
		assertTrue(opHorarioMarcado.isPresent());
		hm = opHorarioMarcado.get();

		assertEquals(hm.getCodigoHorario(), HORARIO_MARCADO_CODIGO_HORARIO_2);
		assertEquals(hm.getCodigoBarbeiro(), HORARIO_MARCADO_CODIGO_BARBEIRO_2);
		assertEquals(hm.getCodigoCliente(), HORARIO_MARCADO_CODIGO_CLIENTE_2);
		codigoAdicionado1 = opHorarioMarcado.get().getCodigo();
		System.out.println("test02_Insert2, OK");
	}

	@Test
	public void test03_Update2() {
		Optional<HorarioMarcado> opHorarioMarcado = horarioMarcadoRepository.findByCodigo(codigoAdicionado2);
		assertTrue(opHorarioMarcado.isPresent());
		HorarioMarcado hm = opHorarioMarcado.get();
		hm.setCodigoBarbeiro(HORARIO_MARCADO_CODIGO_BARBEIRO_2_ALTERADO);
		opHorarioMarcado = horarioMarcadoRepository.save(hm);
		assertTrue(opHorarioMarcado.isPresent());
		hm = opHorarioMarcado.get();
		assertEquals(hm.getCodigoBarbeiro(), HORARIO_MARCADO_CODIGO_BARBEIRO_2_ALTERADO);
		System.out.println("test03_Update2, Ok");
	}

	@Test
	public void test04_List() {
		System.out.println("List . . .");
		List<HorarioMarcado> lista = horarioMarcadoRepository.findAll();
		assertEquals(lista.size(), 2);
		System.out.println("Test04_List, Ok");
	}

	@Test
	public void test05_delete1() {
		Optional<HorarioMarcado> opHorarioMarcado = horarioMarcadoRepository.findByCodigo(codigoAdicionado1);
		assertTrue(opHorarioMarcado.isPresent());
		horarioMarcadoRepository.delete(opHorarioMarcado.get());

		opHorarioMarcado = horarioMarcadoRepository.findByCodigo(codigoAdicionado1);
		assertFalse(opHorarioMarcado.isPresent());
		System.out.println("Test05_delete1, Ok");
	}
}
