package br.com.appbarbearia.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.appbarbearia.model.Barbearia;
import br.com.appbarbearia.repository.BarbeariaRepository;

public class BarbeariaService {

    Logger LOG = Logger.getLogger(BarbeariaService.class.getName());

    @Autowired
    BarbeariaRepository barbeariaRepository;

    public Optional<Barbearia> save(Barbearia barbearia){
        Optional<Barbearia> opBarbearia = barbeariaRepository.save(barbearia);
        if(opBarbearia.isPresent()){
            return opBarbearia;
        }
        return Optional.empty();
    }

    public Optional<Barbearia> findByCodigo(long codigo) {
        Optional<Barbearia> opBarbearia = barbeariaRepository.findByCodigo(codigo);
        if (opBarbearia.isPresent()){
            return opBarbearia;
        }
        return Optional.empty();
    }

    public int delete(Barbearia barbearia) {
        int registrosAfetados = barbeariaRepository.delete(barbearia);
        if (registrosAfetados > 0) {
            LOG.info("Foram excluidos " + registrosAfetados + " regitrados no banco (Tabela: Barbearia)");
        } else {
            LOG.info("Não fo excluido nenhum registro do banco (Tabela: Barbearia)");
        }
        return registrosAfetados;
    }

    public Optional<Barbearia> update(Barbearia barbearia) {
        Optional<Barbearia> opBarbearia= barbeariaRepository.findByCodigo(barbearia.getCodigo());
        if (opBarbearia.isPresent()){
            opBarbearia = save(barbearia);
            if (opBarbearia.isPresent()){
                LOG.info("Barbearia alterado: " + opBarbearia.get().toString());
                return opBarbearia;
        } else {
            LOG.info("Barbearia informada não foi encontrada, portanto, não foi alterado");
            return Optional.empty();
        }
        }
        LOG.warning("Barbearia não foi encontrada, nenhum registro foi alterado");
        return Optional.empty();

    }
}