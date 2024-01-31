package br.com.sqs.bridge.service;

import java.util.List;
import java.util.Optional;

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

    public List<Cliente> obterTodosOsClientes(String emailUsuario) {
	return repository.findByCreatedByOrderByNome(emailUsuario);
    }

    public Cliente obterClienteEspecifico(String nome, String emailUsuario) {
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

    public List<Cliente> findByNome(String clienteNome, String emailUsuario) {
	if (clienteNome == null || clienteNome.isBlank())
	    return obterTodosOsClientes(emailUsuario);

	return repository.findByNomeContainingAndCreatedByOrderByNomeAsc(clienteNome.trim(), emailUsuario);
    }

    public Optional<Cliente> findById(Integer id, String emailUsuario) {
	return repository.findByIdAndCreatedBy(id, emailUsuario);
    }
}
