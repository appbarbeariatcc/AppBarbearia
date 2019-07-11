package br.com.appbarbearia.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.appbarbearia.model.Cliente;
import br.com.appbarbearia.repository.ClienteRepository;

public class ClienteService {

    Logger LOG = Logger.getLogger(Cliente.class.getName());

    @Autowired
    ClienteRepository clienteRepository;

    public Optional<Cliente> save(Cliente cliente) {
        Optional<Cliente> opCliente = clienteRepository.save(cliente);
        if (opCliente.isPresent()) {
            LOG.info("Cliente foi salvo com sucesso: " + opCliente.get().toString());
            return opCliente;
        }
        return Optional.empty();
    }

    public Optional<Cliente> findByCodigo(long codigo) {
        Optional<Cliente> opCliente = clienteRepository.findByCodigo(codigo);
        if (opCliente.isPresent()) {
            LOG.info("Cliente de codigo" + codigo + ": " + opCliente.get().toString());
            return opCliente;
        }
        LOG.info("Cliente de código" + codigo + "não foi encontrado");
        return Optional.empty();
    }

    public Optional<Cliente> update(Cliente cliente) {
        // Verifica se o cliente com o código passado para o update realmente existe no
        // banco
        Optional<Cliente> opCliente = clienteRepository.findByCodigo(cliente.getCodigo());
        if (opCliente.isPresent()) {
            opCliente = save(cliente);
            if (opCliente.isPresent()) {
                LOG.info("Cliente alterado: " + opCliente.get().toString());
                return opCliente;
            }
        }
        LOG.warning("Cliente não foi encontrado, nenhum registro foi alterado");;
        return Optional.empty();
    }

    public int delete(Cliente cliente) {
        // Verifica se o cliente com o código passado para o delete realmente existe no
        // banco
        Optional<Cliente> opCliente = clienteRepository.findByCodigo(cliente.getCodigo());
        if (opCliente.isPresent()) {
            int registrosAfetados = clienteRepository.delete(cliente);
            LOG.info("Foram excluidos " + registrosAfetados + "registros da tabela de clientes");
            return registrosAfetados;
        }
        LOG.warning("Cliente não foi encontrado, nenhum registro foi excluido");
        return 0;
    }
}