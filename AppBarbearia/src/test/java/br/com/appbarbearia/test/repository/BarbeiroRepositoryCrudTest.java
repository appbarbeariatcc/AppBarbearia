package br.com.appbarbearia.test.repository;

import java.util.Calendar;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.appbarbearia.AppBarbearia.RunConfiguration;
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
	public static final int BARBEIRO_TELEFONE_1 = 46021192 ;
	public static final int BARBEIRO_CELULAR_1 = 972932872 ;	
	
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	CidadeRepository cidadeRepository;
	

}
	