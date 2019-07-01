package br.com.appbarbearia.AppBarbearia;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

//@Configuration
//@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages= "br.com.appbarbearia.*")
//@PropertySource(value = "application.properties")
public class RunConfiguration extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(RunConfiguration.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	@PostConstruct
    void started() {
      TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
	
	private static Class<RunConfiguration> applicationClass = RunConfiguration.class;
}
