package br.com.appbarbearia.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.appbarbearia.model.Barbeiro;
import br.com.appbarbearia.repository.BarbeiroRepository;

public class BarbeiroService {

    Logger LOG = Logger.getLogger(BarbeiroService.class.getName());

    @Autowired
    BarbeiroRepository barbeiroRepository;

    public Optional<Barbeiro> save(Barbeiro barbeiro) {
        Optional<Barbeiro> opBarbeiro = barbeiroRepository.save(barbeiro);
        if (opBarbeiro.isPresent()) {
            return opBarbeiro;
        }
        return Optional.empty();
    }

    public Optional<Barbeiro> findByCodigo(long codigo) {
        Optional<Barbeiro> opBarbeiro = barbeiroRepository.findByCodigo(codigo);
        if (opBarbeiro.isPresent()) {
            return opBarbeiro;
        }
        return Optional.empty();
    }

    public int delete(Barbeiro barbeiro) {
        int registrosAfetados = barbeiroRepository.delete(barbeiro);
        if (registrosAfetados > 0) {
            LOG.info("Foram excluidos " + registrosAfetados + " registros do banco (Tabela: Barbeiro)");
        } else {
            LOG.info("Não foi excluido nenhum registro do banco (Tabela: Barbeiro)");
        }
        return registrosAfetados;
    }

    public Optional<Barbeiro> update(Barbeiro barbeiro) {
        Optional<Barbeiro> opBarbeiro = barbeiroRepository.findByCodigo(barbeiro.getCodigo());
        if (opBarbeiro.isPresent()) {
            // Chama o método save, pois a repository já trata se é um save ou update
            opBarbeiro = save(barbeiro);
            if (opBarbeiro.isPresent()) {
                LOG.info("Barbeiro alterado: " + opBarbeiro.get().toString());
                return opBarbeiro;
            }
        }
        LOG.info("Barbeiro informado não foi encontrado, portanto, não foi alterado");
        return Optional.empty();
    }
}