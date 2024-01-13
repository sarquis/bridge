package br.com.sqs.bridge.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

}
