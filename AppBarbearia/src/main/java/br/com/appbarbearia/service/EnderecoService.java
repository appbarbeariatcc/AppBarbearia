package br.com.appbarbearia.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.appbarbearia.model.Endereco;
import br.com.appbarbearia.repository.EnderecoRepository;

public class EnderecoService {

    Logger LOG = Logger.getLogger(EnderecoService.class.getName());

    @Autowired
    EnderecoRepository enderecoRepository;

    public Optional<Endereco> save(Endereco endereco) {
        Optional<Endereco> opEndereco = enderecoRepository.save(endereco);
        if (opEndereco.isPresent()) {
            LOG.info("Endereço foi salvo com sucesso: " + opEndereco.get().toString());
            return opEndereco;
        }
        return Optional.empty();
    }

    public Optional<Endereco> findByCodigo(int codigo) {
        Optional<Endereco> opEndereco = enderecoRepository.findByCodigo(codigo);
        if (opEndereco.isPresent()) {
            LOG.info("Cliente de codigo " + codigo + ": " + opEndereco.get().toString());
            return opEndereco;
        }
        LOG.info("Endereço " + codigo + "não foi encontrado");
        return Optional.empty();
    }

    public Optional<Endereco> update(Endereco endereco) {
        Optional<Endereco> opEndereco = enderecoRepository.findByCodigo(endereco.getCodigo());
        if (opEndereco.isPresent()) {
            opEndereco = save(endereco);
            if (opEndereco.isPresent()) {
                LOG.info("Endereço alterado: " + opEndereco.get().toString());
                return opEndereco;
            }
        }
        LOG.warning("Endereço não foi encontrado, nenhum registro foi alterado");
        return Optional.empty();
    }

    public int delete(Endereco endereco){
        Optional<Endereco> opEndereco = enderecoRepository.findByCodigo(endereco.getCodigo());
        if (opEndereco.isPresent()) {
            int registrosAfetados = enderecoRepository.delete(endereco);
            LOG.info("Foram exlucidos " + registrosAfetados + "registros da tabela de Endereço");
            return registrosAfetados;
        }
        LOG.warning("Endereço não foi encontrado, nenhum registro foi excluido");
        return 0;
    }
    
}