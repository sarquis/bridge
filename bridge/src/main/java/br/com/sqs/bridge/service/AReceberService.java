package br.com.sqs.bridge.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sqs.bridge.model.entity.AReceber;
import br.com.sqs.bridge.model.entity.Cliente;
import br.com.sqs.bridge.repository.AReceberRepository;
import br.com.sqs.bridge.util.BridgeException;

@Service
public class AReceberService {

    private AReceberRepository repository;
    private ClienteService clienteService;

    public AReceberService(AReceberRepository repository, ClienteService clienteService) {
	this.repository = repository;
	this.clienteService = clienteService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvar(AReceber aReceber, String emailUsuario) throws BridgeException {
	validarESalvarClienteSeNecessario(aReceber, emailUsuario);
	aReceber.setAtivo(true);
	repository.save(aReceber);
    }

    private void validarESalvarClienteSeNecessario(AReceber aReceber, String atualizar) throws BridgeException {
	Cliente cliente = aReceber.getCliente();

	if (cliente == null || cliente.getNome() == null || cliente.getNome().isBlank())
	    throw new BridgeException("O nome do cliente está vazio.");

	Cliente clienteEncontrado = clienteService.obterCliente(aReceber.getCliente().getNome(), atualizar);

	if (clienteEncontrado == null) {
	    cliente.setSaldo(aReceber.getValor().negate());
	    clienteService.salvarNovoCliente(cliente);
	} else {
	    clienteEncontrado.setSaldo(clienteEncontrado.getSaldo().subtract(aReceber.getValor()));
	    clienteService.atualizar(clienteEncontrado);
	    aReceber.setCliente(clienteEncontrado);
	}
    }

    public List<AReceber> findAllWithCliente(String emailUsuario) {
	return repository.findByCreatedByWithCliente(emailUsuario);
    }

    public List<AReceber> findByCliente(String clienteNome, String emailUsuario) {
	if (clienteNome == null || clienteNome.isBlank())
	    return findAllWithCliente(emailUsuario);

	return repository.findByCliente(clienteNome.trim(), emailUsuario);
    }

}
