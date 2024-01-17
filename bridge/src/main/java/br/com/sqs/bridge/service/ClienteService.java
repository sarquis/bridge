package br.com.sqs.bridge.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sqs.bridge.model.entity.Cliente;
import br.com.sqs.bridge.repository.ClienteRepository;

@Service
public class ClienteService {

    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
	this.repository = repository;
    }

    public List<Cliente> obterClientes(String emailUsuario) {
	return repository.findByCreatedByOrderByNome(emailUsuario);
    }

    public Cliente obterCliente(String nome, String emailUsuario) {
	return repository.findByNomeAndCreatedBy(nome, emailUsuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvarNovoCliente(Cliente cliente) {
	cliente.setAtivo(true);
	repository.save(cliente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void atualizar(Cliente cliente) {
	repository.save(cliente);
    }
}
