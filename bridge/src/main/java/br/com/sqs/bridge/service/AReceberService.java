package br.com.sqs.bridge.service;

import java.util.List;
import java.util.Optional;

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
    public void salvarObservacoes(AReceber aReceberTemp, String emailUsuario) throws BridgeException {
	// Garantindo que só altere a observação.
	AReceber aReceber = repository.findByIdAndCreatedBy(aReceberTemp.getId(), emailUsuario).get();
	aReceber.setObservacoes(aReceberTemp.getObservacoes());
	repository.save(aReceber);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvarNovo(AReceber aReceber, String emailUsuario) throws BridgeException {
	validarESalvarClienteSeNecessario(aReceber, emailUsuario);
	aReceber.setId(null);
	aReceber.setAtivo(true);
	repository.save(aReceber);
    }

    private void validarESalvarClienteSeNecessario(AReceber aReceber, String emailUsuario) throws BridgeException {
	String nomeDoCliente = aReceber.getCliente().getNome();
	if (nomeDoCliente == null || nomeDoCliente.isBlank())
	    throw new BridgeException("O nome do cliente está vazio.");

	Cliente clienteEncontrado = clienteService.obterClienteEspecifico(nomeDoCliente, emailUsuario);
	if (clienteEncontrado == null) {
	    aReceber.getCliente().setSaldo(aReceber.getValor().negate()); // Definindo o saldo inicial.
	    clienteService.salvarNovoCliente(aReceber.getCliente());
	} else {
	    // Atualizando o saldo:
	    clienteEncontrado.setSaldo(clienteEncontrado.getSaldo().subtract(aReceber.getValor()));
	    clienteService.atualizar(clienteEncontrado);
	    aReceber.setCliente(clienteEncontrado);
	}
    }

    public List<AReceber> findByCreatedByWithCliente(String emailUsuario) {
	return repository.findByCreatedByWithCliente(emailUsuario);
    }

    public List<AReceber> findByCreatedByAndClienteNomeContainingWithCliente(String clienteNome, String emailUsuario) {
	if (clienteNome == null || clienteNome.isBlank())
	    return findByCreatedByWithCliente(emailUsuario);

	return repository.findByCreatedByAndClienteNomeContainingWithCliente(clienteNome.trim(), emailUsuario);
    }

    public Optional<AReceber> findByIdAndCreatedByWithCliente(Long id, String emailUsuario) {
	return repository.findByIdAndCreatedByWithCliente(id, emailUsuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir(long id, String emailUsuario) {
	AReceber aReceber = repository.findByIdAndCreatedByWithCliente(id, emailUsuario).get();

	Cliente cliente = aReceber.getCliente();
	cliente.setSaldo(cliente.getSaldo().add(aReceber.getValor())); // Devolvendo saldo do cliente.
	clienteService.atualizar(cliente);

	repository.delete(aReceber);
    }

}
