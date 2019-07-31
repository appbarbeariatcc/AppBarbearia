package br.com.appbarbearia.engine;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.appbarbearia.model.Horario;
import br.com.appbarbearia.repository.HorarioRepository;

public class HorarioEngine {

    @Autowired
    HorarioRepository horarioRepository;

    public void criarHorarios(){

        Horario horario = new Horario();
        Calendar calendar = Calendar.getInstance();

        horario.setDescricao("MEIA NOITE");
        calendar.set(1980, 1, 1, 0, 0, 0);
        horarioRepository.save(horario);
        
        horario = new Horario();
        horario.setDescricao("MEIA NOITE E QUINZE MINUTOS");
        calendar.set(1980, 1, 1, 0, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("MEIA NOITE E MEIA");
        calendar.set(1980, 1, 1, 0, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("MEIA NOITE E QUARENTA E CINCO MINUTOS");
        calendar.set(1980, 1, 1, 0, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("UMA HORA DA MANHA");
        calendar.set(1980, 1, 1, 1, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("UMA HORA E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 1, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("UMA HORA E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 1, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("UMA HORA E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 1, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DUAS HORAS DA MANHA");
        calendar.set(1980, 1, 1, 2, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DUAS HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 2, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DUAS HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 2, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DUAS HORAS E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 2, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("TRES HORAS DA MANHA");
        calendar.set(1980, 1, 1, 3, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("TRES HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 3, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("TRES HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 3, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("TRES HORAS E QUARENTA E CINCO MINUTOS DA MANHA ");
        calendar.set(1980, 1, 1, 3, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("QUATROS HORAS DA MANHA");
        calendar.set(1980, 1, 1, 4, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("QUATROS HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 4, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("QUATROS HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 4, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("QUATROS HORAS E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 4, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("CINCO HORAS DA MANHA");
        calendar.set(1980, 1, 1, 5, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("CINCO HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 5, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("CINCO HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 5, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("CINCO HORAS E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 5, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SEIS HORAS DA MANHA");
        calendar.set(1980, 1, 1, 6, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SEIS HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 6, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SEIS HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 6, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SEIS HORAS E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 6, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SETE HORAS DA MANHA");
        calendar.set(1980, 1, 1, 7, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SETE HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 7, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SETE HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 7, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SETE HORAS E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 7, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("OITO HORAS DA MANHA");
        calendar.set(1980, 1, 1, 8, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("OITO HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 8, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("OITO HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 8, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("OITO HORAS E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 8, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("NOVE HORAS DA MANHA");
        calendar.set(1980, 1, 1, 9, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("NOVE HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 9, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("NOVE HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 9, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("NOVE HORAS E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 9, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DEZ HORAS DA MANHA");
        calendar.set(1980, 1, 1, 10, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DEZ HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 10, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DEZ HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 10, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DEZ HORAS E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 10, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("ONZE HORAS DA MANHA");
        calendar.set(1980, 1, 1, 11, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("ONZE HORAS E QUINZE MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 11, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("ONZE HORAS E MEIA DA MANHA");
        calendar.set(1980, 1, 1, 11, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("ONZE HORAS E QUARENTA E CINCO MINUTOS DA MANHA");
        calendar.set(1980, 1, 1, 11, 45, 0);
        horarioRepository.save(horario);      
        
        horario = new Horario();
        horario.setDescricao("MEIO DIA");
        calendar.set(1980, 1, 1, 12, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("MEIO DIA E QUINZE MINUTOS");
        calendar.set(1980, 1, 1, 12, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("MEIO DIA E MEIA");
        calendar.set(1980, 1, 1, 12, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("MEIO DIA E QUARENTA E CINCO MINUTOS");
        calendar.set(1980, 1, 1, 12, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("UMA HORA DA TARDE");
        calendar.set(1980, 1, 1, 13, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("UMA HORA E QUINZE MIUNTOS DA TARDE");
        calendar.set(1980, 1, 1, 13, 15 ,0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("UMA HORA E MEIA DA TARDE");
        calendar.set(1980, 1, 1, 13, 30, 0);
        horarioRepository.save(horario);   

        horario = new Horario();
        horario.setDescricao("UMA HORA E QUARENTA E CINCO MINUTOS DA TARDE");
        calendar.set(1980, 1, 1, 13, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DUAS HORAS DA TARDE");
        calendar.set(1980, 1, 1, 14, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DUAS HORAS E QUINZE MINUTOS DA TARDE");
        calendar.set(1980, 1, 1, 14, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DUAS HORAS E MEIA DA TARDE DA TARDE");
        calendar.set(1980, 1, 1, 14, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DUAS HORAS E QUARENTA E CINCO MINUTOS DA TARDE");
        calendar.set(1980, 1, 1, 14, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("TRES HORAS DA TARDE");
        calendar.set(1980, 1, 1, 15, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("TRES HORAS E QUINZE MINUTOS TARDE");
        calendar.set(1980, 1, 1, 15, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("TRES HORAS E MEIA DA TARDE");
        calendar.set(1980, 1, 1, 15, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("TRES HORAS E QUARENTA E CINCO MINUTOS TARDE");
        calendar.set(1980, 1, 1, 15, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("QUATRO HORAS DA TARDE");
        calendar.set(1980, 1, 1, 16, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("QUATRO HORAS E QUINZE MINUTOS TARDE");
        calendar.set(1980, 1, 1, 16, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("QUATRO HORAS E MEIA DA TARDE");
        calendar.set(1980, 1, 1, 16, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("QUATRO HORAS E QUARENTA E CINCO MINUTOS TARDE");
        calendar.set(1980, 1, 1, 16, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("CINCO HORAS DA TARDE");
        calendar.set(1980, 1, 1, 17, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("CINCO HORAS E QUINZE MINUTOS TARDE");
        calendar.set(1980, 1, 1, 17, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("CINCO HORAS E MEIA DA TARDE");
        calendar.set(1980, 1, 1, 17, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("CINCO HORAS E QUARENTA E CINCO MINUTOS TARDE");
        calendar.set(1980, 1, 1, 17, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SEIS HORAS DA NOITE");
        calendar.set(1980, 1, 1, 18, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SEIS HORAS E QUINZE MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 18, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SEIS HORAS E MEIA DA NOITE");
        calendar.set(1980, 1, 1, 18, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SEIS HORAS E QUARENTA E CINCO MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 18, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SETE HORAS DA NOITE");
        calendar.set(1980, 1, 1, 19, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SETE HORAS E QUINZE MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 19, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SETE HORAS E MEIA DA NOITE");
        calendar.set(1980, 1, 1, 19, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("SETE HORAS E QUARENTA E CINCO MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 19, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("OITO HORAS DA NOITE");
        calendar.set(1980, 1, 1, 20, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("OITO HORAS E QUINZE MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 20, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("OITO HORAS E MEIA DA NOITE");
        calendar.set(1980, 1, 1, 20, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("OITO HORAS E QUARENTA E CINCO MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 20, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("NOVE HORAS DA NOITE");
        calendar.set(1980, 1, 1, 21, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("NOVE HORAS E QUINZE MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 21, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("NOVE HORAS E MEIA DA NOITE");
        calendar.set(1980, 1, 1, 21, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("NOVE HORAS E QUARENTA E CINCO MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 21, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DEZ HORAS DA NOITE");
        calendar.set(1980, 1, 1, 22, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DEZ HORAS E QUINZE MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 22, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DEZ HORAS E MEIA DA NOITE");
        calendar.set(1980, 1, 1, 22, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("DEZ HORAS E QUARENTA E CINCO MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 22, 45, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("ONZE HORAS DA NOITE");
        calendar.set(1980, 1, 1, 23, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("ONZE HORAS DA NOITE");
        calendar.set(1980, 1, 1, 23, 0, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("ONZE HORAS E QUINZE MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 23, 15, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("ONZE HORAS E MEIA DA NOITE");
        calendar.set(1980, 1, 1, 23, 30, 0);
        horarioRepository.save(horario);

        horario = new Horario();
        horario.setDescricao("ONZE HORAS E QUARENTA E CINCO MINUTOS DA NOITE");
        calendar.set(1980, 1, 1, 23, 45, 0);
        horarioRepository.save(horario);







    }

    
}