package br.com.appbarbearia.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.appbarbearia.model.Barbearia;
import br.com.appbarbearia.model.Horario;
import br.com.appbarbearia.model.HorarioMarcado;
import br.com.appbarbearia.repository.HorarioMarcadoRepository;
import br.com.appbarbearia.repository.HorarioRepository;

public class HorarioMarcadoService {

    Logger LOG = Logger.getLogger(HorarioMarcadoService.class.getName());

    @Autowired
    HorarioMarcadoRepository horarioMarcadoRepository;

    @Autowired
    HorarioRepository horarioRepository;

    public Optional<HorarioMarcado> save(HorarioMarcado horarioMarcado) {
        Optional<HorarioMarcado> opHorarioMarcado = horarioMarcadoRepository.save(horarioMarcado);
        if (opHorarioMarcado.isPresent()) {
            LOG.info("Horario marcado salvo com sucesso!");
            return opHorarioMarcado;
        }
        LOG.severe("Horario marcado não foi salvo");
        return Optional.empty();
    }

    public Optional<HorarioMarcado> findByCodigo(long codigo) {
        Optional<HorarioMarcado> opHorarioMarcado = horarioMarcadoRepository.findByCodigo(codigo);
        if (opHorarioMarcado.isPresent()) {
            LOG.info("Horario de código " + codigo + ": " + opHorarioMarcado.get().toString());
            return opHorarioMarcado;
        }
        LOG.info("Horario de código" + codigo + "não encontrado");
        return Optional.empty();
    }

    public Optional<HorarioMarcado> update(HorarioMarcado horarioMarcado) {
        Optional<HorarioMarcado> opHorarioMarcado = horarioMarcadoRepository.findByCodigo(horarioMarcado.getCodigo());
        if (opHorarioMarcado.isPresent()) {
            opHorarioMarcado = save(horarioMarcado);
            if (opHorarioMarcado.isPresent()) {
                LOG.info("Horario marcado alterado:" + opHorarioMarcado.get().toString());
                return opHorarioMarcado;
            }
        }
        LOG.warning("Horario marcado não foi encontrado, portanto não foi alterado");
        return Optional.empty();
    }

    public int delete(HorarioMarcado horarioMarcado) {
        Optional<HorarioMarcado> opHorarioMarcado = horarioMarcadoRepository.findByCodigo(horarioMarcado.getCodigo());
        if (opHorarioMarcado.isPresent()) {
            int registrosAfetados = horarioMarcadoRepository.delete(horarioMarcado);
            LOG.info("Foram excluidos " + registrosAfetados + "registros da tabela de horarios marcados");
            return registrosAfetados;
        }
        LOG.warning("Horario marcado não encontrado, nenhum registro excluido");
        return 0;
    }

    public List<Horario> listaHorariosDisponiveisBarbearia(Barbearia barbearia, Date date) {
        /**
         * Será usado para mostrar os horarios disponiveis, na tela de marcar horario,
         * irá aparecer apenas os disponiveis, por isso não será necessário fazer
         * validação no save para ver se existe 2 horarios com conflito
         */
        List<Horario> horarios = listaHorariosEntreAberturaEFechamento(barbearia);
        List<HorarioMarcado> horariosMarcados = listaHorariosMarcadosBarbearia(barbearia, date);
        List<Horario> horariosDisponiveis = new ArrayList<>();
        horariosMarcados.stream().forEach(hm -> {
            Optional<Horario> opHorario = horarios.stream().filter(h -> h.getCodigo() == hm.getCodigoHorario())
                    .findAny();
            if (!opHorario.isPresent()) {
                horariosDisponiveis.add(opHorario.get());
            }
        });
        if (horariosDisponiveis != null && !horariosDisponiveis.isEmpty()) {
            return horariosDisponiveis;
        } else {
            return new ArrayList<>();
        }
    }

    private List<HorarioMarcado> listaHorariosMarcadosBarbearia(Barbearia barbearia, Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int idx = 0;
        String where = " WHERE BARBEARIA_CODIGO=? AND DIA=?";
        Object[] args = new Object[2];
        String dia = dateFormat.format(date);
        args[idx++] = barbearia.getCodigo();
        args[idx++] = dia;
        List<HorarioMarcado> horariosMarcados = horarioMarcadoRepository.list(where, args);
        if (horariosMarcados != null && !horariosMarcados.isEmpty()) {
            return horariosMarcados;
        }
        return new ArrayList<>();
    }

    private List<Horario> listaHorariosEntreAberturaEFechamento(Barbearia barbearia) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Object[] args = new Object[2];
        int idx = 0;
        String horarioAbertura = df.format(barbearia.getHorarioAbertura());
        String horarioFechamento = df.format(barbearia.getHorarioFechamento());
        String where = " WHERE HORA BETWEEN ? AND ? ";
        args[idx++] = horarioAbertura;
        args[idx++] = horarioFechamento;
        List<Horario> horarios = horarioRepository.list(where, args);
        if (horarios != null && !horarios.isEmpty()) {
            return horarios;
        }
        return new ArrayList<>();
    }
}