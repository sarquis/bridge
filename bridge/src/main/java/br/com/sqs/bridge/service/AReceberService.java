package br.com.sqs.bridge.service;

import java.math.BigDecimal;
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
    public void salvarObservacoes(AReceber aReceber, String emailUsuario) throws BridgeException {
	// Garantindo que só altere a observação.
	String novasObservacoes = aReceber.getObservacoes();
	aReceber = repository.findByIdAndCreatedBy(aReceber.getId(), emailUsuario).get();
	aReceber.setObservacoes(novasObservacoes);
	repository.save(aReceber);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvarNovo(AReceber aReceber, String emailUsuario) throws BridgeException {
	aReceber.setId(null);
	validarESalvarClienteSeNecessario(aReceber, emailUsuario);
	aReceber.setAtivo(true);
	repository.save(aReceber);
    }

    private void validarESalvarClienteSeNecessario(AReceber aReceber, String atualizar) throws BridgeException {
	Cliente cliente = aReceber.getCliente();

	if (cliente == null || cliente.getNome() == null || cliente.getNome().isBlank())
	    throw new BridgeException("O nome do cliente está vazio.");

	Cliente clienteEncontrado = clienteService.obterClienteEspecifico(aReceber.getCliente().getNome(), atualizar);

	if (clienteEncontrado == null) {
	    cliente.setSaldo(aReceber.getValor().negate());
	    clienteService.salvarNovoCliente(cliente);
	} else {
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
	BigDecimal saldoAnterior = cliente.getSaldo();
	cliente.setSaldo(saldoAnterior.add(aReceber.getValor())); // Devolvendo saldo do cliente.
	clienteService.atualizar(cliente);

	repository.delete(aReceber);
    }

}
