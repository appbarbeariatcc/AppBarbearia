package br.com.appbarbearia.test.service;

import static org.junit.Assert.assertTrue;

import java.util.Date;
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
import br.com.appbarbearia.model.HorarioMarcado.HorarioMarcadoBuilder;
import br.com.appbarbearia.service.HorarioMarcadoService;
import br.com.appbarbearia.test.repository.RepositoryJdbcTestHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HorarioMarcadoServiceLogicTest {

    @Autowired
    HorarioMarcadoService horarioMarcadoService;

    @Autowired
    RepositoryJdbcTestHelper rjth;

    @Test
    public void test00_CleanUp(){
        rjth.limpaBancoDeDados();
        rjth.criarCidade();
        rjth.criarBarbeiro();
        rjth.criarBarbearia();
        rjth.criarHorario();
    }
    

    @Test
    public void test01_save(){
        HorarioMarcadoBuilder builder = HorarioMarcado.builder();
        HorarioMarcado horarioMarcado = builder.withCodigoBarbeiro(1).withCodigoCliente(1).withCodigoHorario(1).withDia(new Date()).build();

        Optional<HorarioMarcado> opHorarioMarcado = horarioMarcadoService.save(horarioMarcado);
        assertTrue(opHorarioMarcado.isPresent());
    }
}