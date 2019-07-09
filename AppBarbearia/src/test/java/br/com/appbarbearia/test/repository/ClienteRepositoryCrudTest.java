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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.appbarbearia.AppBarbearia.RunConfiguration;
import br.com.appbarbearia.model.Cliente;
import br.com.appbarbearia.repository.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunConfiguration.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/**
 * ClienteRepositoryCrudTest
 */
public class ClienteRepositoryCrudTest {

    public static final int CLIENTE_CODIGO_CIDADE_1 = 1;
    public static final String CLIENTE_NOME_1 = "Lucas";
    public static final String CLIENTE_RG_1 = "50.037.334-6";
    public static final String CLIENTE_CPF_1 = "433.044.988-93";
    public static final Integer CLIENTE_TELEFONE_1 = 46021192;
    public static final int CLIENTE_CELULAR_1 = 972932872;

    public static final int CLIENTE_CODIGO_CIDADE_2 = 1;
    public static final String CLIENTE_NOME_2 = "Luis";
    public static final String CLIENTE_RG_2 = "11.111.111-1";
    public static final String CLIENTE_CPF_2 = "111.111.111-11";
    public static final Integer CLIENTE_TELEFONE_2 = 46021192;
    public static final int CLIENTE_CELULAR_2 = 943042874;

    public static final String CLIENTE_NOME_2_ALTERADO = "João";
    public static final Integer CLIENTE_TELEFONE_2_ALTERADO = 46024232;
    public static final int CLIENTE_CELULAR_2_ALTERADO = 984201142;

    private static long codigoAdicionado1 = 0;
    private static long codigoAdicionado2 = 0;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

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
        Cliente cliente = new Cliente();
        Calendar calendar = Calendar.getInstance();
        cliente.setCodigoCidade(CLIENTE_CODIGO_CIDADE_1);
        cliente.setNome(CLIENTE_NOME_1);
        cliente.setRg(CLIENTE_RG_1);
        cliente.setCpf(CLIENTE_CPF_1);
        cliente.setTelefone(CLIENTE_TELEFONE_1);
        cliente.setCelular(CLIENTE_CELULAR_1);
        calendar.set(1997, 06, 20);
        cliente.setDataNascimento(calendar.getTime());

        Optional<Cliente> opCliente = clienteRepository.save(cliente);
        assertTrue(opCliente.isPresent());

        cliente = opCliente.get();

        assertEquals(cliente.getCodigoCidade(), CLIENTE_CODIGO_CIDADE_1);
        assertEquals(cliente.getNome(), CLIENTE_NOME_1);
        assertEquals(cliente.getRg(), CLIENTE_RG_1);
        assertEquals(cliente.getCpf(), CLIENTE_CPF_1);
        assertEquals(cliente.getTelefone(), CLIENTE_TELEFONE_1);
        assertEquals(cliente.getCelular(), CLIENTE_CELULAR_1);
        codigoAdicionado1 = opCliente.get().getCodigo();
        System.out.println("teste01_Insert1, Ok");
    }

    @Test
    public void test02_Insert1() {
        Cliente cliente = new Cliente();
        Calendar calendar = Calendar.getInstance();
        cliente.setCodigoCidade(CLIENTE_CODIGO_CIDADE_2);
        cliente.setNome(CLIENTE_NOME_2);
        cliente.setRg(CLIENTE_RG_2);
        cliente.setCpf(CLIENTE_CPF_2);
        cliente.setTelefone(CLIENTE_TELEFONE_2);
        cliente.setCelular(CLIENTE_CELULAR_2);
        calendar.set(1998, 06, 20);
        cliente.setDataNascimento(calendar.getTime());

        Optional<Cliente> opCliente = clienteRepository.save(cliente);
        assertTrue(opCliente.isPresent());

        cliente = opCliente.get();

        assertEquals(cliente.getCodigoCidade(), CLIENTE_CODIGO_CIDADE_2);
        assertEquals(cliente.getNome(), CLIENTE_NOME_2);
        assertEquals(cliente.getRg(), CLIENTE_RG_2);
        assertEquals(cliente.getCpf(), CLIENTE_CPF_2);
        assertEquals(cliente.getTelefone(), CLIENTE_TELEFONE_2);
        assertEquals(cliente.getCelular(), CLIENTE_CELULAR_2);
        codigoAdicionado2 = opCliente.get().getCodigo();
        System.out.println("teste01_Insert1, Ok");
    }

    @Test
    public void test03_Update2() {
        Optional<Cliente> opCliente = clienteRepository.findByCodigo(codigoAdicionado2);
        assertTrue(opCliente.isPresent());

        Cliente cliente = opCliente.get();
        cliente.setNome(CLIENTE_NOME_2_ALTERADO);
        cliente.setTelefone(CLIENTE_TELEFONE_2_ALTERADO);
        cliente.setCelular(CLIENTE_CELULAR_2_ALTERADO);
        assertTrue(opCliente.isPresent());

        cliente = opCliente.get();
        assertEquals(cliente.getNome(), CLIENTE_NOME_2_ALTERADO);
        assertEquals(cliente.getTelefone(), CLIENTE_TELEFONE_2_ALTERADO);
        assertEquals(cliente.getCelular(), CLIENTE_CELULAR_2_ALTERADO);
        System.out.println("test03_Update2, Ok");
    }

    @Test
    public void test04_List() {
        System.out.println("List . . .");
        List<Cliente> lista = clienteRepository.findAll();
        assertEquals(lista.size(), 2);
        System.out.println("Test04_List, Ok");
    }

    // @Test
    public void test05_delete01() {
        Optional<Cliente> opCliente = clienteRepository.findByCodigo(codigoAdicionado1);
        assertTrue(opCliente.isPresent());
        clienteRepository.delete(opCliente.get());

        opCliente = clienteRepository.findByCodigo(codigoAdicionado1);
        assertFalse(opCliente.isPresent());
        System.out.println("Test05_delete1, Ok");
    }
}
