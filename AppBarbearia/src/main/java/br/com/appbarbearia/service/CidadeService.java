package br.com.appbarbearia.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.appbarbearia.model.Cidade;
import br.com.appbarbearia.repository.CidadeRepository;

public class CidadeService {

    Logger LOG = Logger.getLogger(CidadeService.class.getName());

    @Autowired 
    CidadeRepository cidadeRepository;

    public Optional<Cidade> save(Cidade cidade) {
        Optional<Cidade> opCidade = cidadeRepository.save(cidade);
        if (opCidade.isPresent()) {
            return opCidade;
        }
        return Optional.empty();
    }

    public Optional<Cidade> findByCodigo(long codigo) {
        Optional<Cidade> opCidade = cidadeRepository.findByCodigo(codigo);
        if (opCidade.isPresent()) {
            return opCidade;
        }
        return Optional.empty();
    }

    public int delete(Cidade cidade) {
        int registrosAfetados = cidadeRepository.delete(cidade);
        if (registrosAfetados > 0 ) {
            LOG.info("Foram excluidos " + registrosAfetados + " registros do banco (Tabela: Cidade)");
        } else {
            LOG.info("Não foi exlcuido nenhum registro do banco (Tabela: Cidade)");
        }
        return registrosAfetados;
    }

    public Optional<Cidade> update(Cidade cidade) {
        Optional<Cidade> opCidade = cidadeRepository.findByCodigo(cidade.getCodigo());
        if (opCidade.isPresent()) {
            opCidade = save(cidade);
            if(opCidade.isPresent()) {
                LOG.info("Cidade alterado: " + opCidade.get().toString());
                return opCidade;
            }
        }
        LOG.warning("Cidade não foi encontrada, nenhum registro foi alterado");
        return Optional.empty();
    }


    
}