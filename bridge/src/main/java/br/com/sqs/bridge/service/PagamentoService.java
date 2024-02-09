package br.com.sqs.bridge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sqs.bridge.model.entity.Cliente;
import br.com.sqs.bridge.model.entity.Pagamento;
import br.com.sqs.bridge.repository.PagamentoRepository;
import br.com.sqs.bridge.util.BridgeException;

@Service
public class PagamentoService {

    private PagamentoRepository repository;
    private ClienteService clienteService;

    public PagamentoService(PagamentoRepository repository, ClienteService clienteService) {
	this.repository = repository;
	this.clienteService = clienteService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvarObservacoes(Pagamento pagamentoTemp, String emailUsuario) throws BridgeException {
	// Garantindo que só altere a observação.
	Pagamento pagamento = repository.findByIdAndCreatedBy(pagamentoTemp.getId(), emailUsuario).get();
	pagamento.setObservacoes(pagamentoTemp.getObservacoes());
	repository.save(pagamento);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvarNovo(Pagamento pagamento, String emailUsuario) throws BridgeException {
	clienteValidarAtualizarSaldo(pagamento, emailUsuario);
	pagamento.setId(null);
	repository.save(pagamento);
    }

    private void clienteValidarAtualizarSaldo(Pagamento pagamento, String emailUsuario) throws BridgeException {
	if (pagamento.getCliente() == null
		|| pagamento.getCliente().getNome() == null
		|| pagamento.getCliente().getNome().isBlank())
	    throw new BridgeException("Por favor, informe um cliente.");

	String nomeDoCliente = pagamento.getCliente().getNome();
	Cliente cliente = clienteService.findByNomeAndCreatedBy(nomeDoCliente, emailUsuario);
	clienteService.adicionarSaldo(cliente, pagamento.getValor());
	pagamento.setCliente(cliente);
    }

    public List<Pagamento> findByCreatedByWithCliente(String emailUsuario) {
	return repository.findByCreatedByWithCliente(emailUsuario);
    }

    public List<Pagamento> findByCreatedByAndClienteNomeContainingWithCliente(String clienteNome, String emailUsuario) {
	if (clienteNome == null || clienteNome.isBlank())
	    return findByCreatedByWithCliente(emailUsuario);

	return repository.findByCreatedByAndClienteNomeContainingWithCliente(clienteNome.trim(), emailUsuario);
    }

    public Optional<Pagamento> findByIdAndCreatedByWithCliente(Long id, String emailUsuario) {
	return repository.findByIdAndCreatedByWithCliente(id, emailUsuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluir(long id, String emailUsuario) {
	Pagamento pagamento = repository.findByIdAndCreatedByWithCliente(id, emailUsuario).get();
	clienteService.removerSaldo(pagamento.getCliente(), pagamento.getValor());
	repository.delete(pagamento);
    }

}
