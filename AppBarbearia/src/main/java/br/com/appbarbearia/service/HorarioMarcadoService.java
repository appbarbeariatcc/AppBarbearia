package br.com.appbarbearia.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.appbarbearia.model.HorarioMarcado;
import br.com.appbarbearia.repository.HorarioMarcadoRepository;

public class HorarioMarcadoService {

    Logger LOG = Logger.getLogger(HorarioMarcadoService.class.getName());

    @Autowired
    HorarioMarcadoRepository horarioMarcadoRepository;

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
}