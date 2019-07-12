package br.com.appbarbearia.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.appbarbearia.model.Horario;
import br.com.appbarbearia.repository.HorarioRepository;

/**
 * HorarioService
 */
public class HorarioService {

    Logger LOG = Logger.getLogger(HorarioService.class.getName());

    @Autowired
    HorarioRepository horarioRepository;

    public Optional<Horario> save(Horario horario) {
        Optional<Horario> opHorario = horarioRepository.save(horario);
        if (opHorario.isPresent()) {
            LOG.info("Horario salvo com sucesso! :" + horario.toString());
            return opHorario;
        }
        LOG.severe("Horario não foi salvo!");
        return Optional.empty();
    }

    public Optional<Horario> findByCodigo(int codigo) {
        Optional<Horario> opHorario = horarioRepository.findByCodigo(codigo);
        if (opHorario.isPresent()) {
            LOG.info("Horario de código " + codigo + ": " + opHorario.get().toString());
            return opHorario;
        }
        LOG.info("Horario de código " + codigo + "não encontrado");
        return Optional.empty();
    }

    public Optional<Horario> update(Horario horario) {
        Optional<Horario> opHorario = horarioRepository.findByCodigo(horario.getCodigo());
        if (opHorario.isPresent()) {
            opHorario = save(horario);
            if (opHorario.isPresent()) {
                LOG.info("Horario alterado com sucesso: " + horarioRepository.toString());
                return opHorario;
            }
        }
        LOG.warning("Horario não foi encontrado, portanto não foi alterado");
        return Optional.empty();
    }

    public int delete(Horario horario) {
        Optional<Horario> opHorario = horarioRepository.findByCodigo(horario.getCodigo());
        if (opHorario.isPresent()) {
            int registrosAfetados = horarioRepository.delete(horario);
            return registrosAfetados;
        }
        LOG.warning("Horario não foi encontrado, nenhum registro foi excluido");
        return 0;
    }
}