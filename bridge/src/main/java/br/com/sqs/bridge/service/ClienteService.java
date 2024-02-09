package br.com.sqs.bridge.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sqs.bridge.model.entity.Cliente;
import br.com.sqs.bridge.repository.ClienteRepository;
import br.com.sqs.bridge.util.BridgeException;

@Service
public class ClienteService {

    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
	this.repository = repository;
    }

    public List<Cliente> findByCreatedByOrderByNome(String emailUsuario) {
	return repository.findByCreatedByOrderByNome(emailUsuario);
    }

    public Cliente findByNomeAndCreatedBy(String nome, String emailUsuario) {
	return repository.findByNomeAndCreatedBy(nome, emailUsuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvarNovoCliente(Cliente cliente, String emailUsuario) throws BridgeException {
	verificarSeNomeJaEstaEmUso(cliente.getNome(), emailUsuario);
	cliente.setId(null);
	repository.save(cliente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void adicionarSaldo(Cliente clienteTemp, BigDecimal valor) {
	// Garantindo que apenas o saldo seja alterado.
	Cliente cliente = repository.findById(clienteTemp.getId()).get();
	cliente.setSaldo(cliente.getSaldo().add(valor));
	repository.save(cliente);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removerSaldo(Cliente clienteTemp, BigDecimal valor) {
	// Garantindo que apenas o saldo seja alterado.
	Cliente cliente = repository.findById(clienteTemp.getId()).get();
	cliente.setSaldo(cliente.getSaldo().subtract(valor));
	repository.save(cliente);
    }

    public List<Cliente> findByNomeContainingAndCreatedByOrderByNomeAsc(String clienteNome, String emailUsuario) {
	if (clienteNome == null || clienteNome.isBlank())
	    return findByCreatedByOrderByNome(emailUsuario);

	return repository.findByNomeContainingAndCreatedByOrderByNomeAsc(clienteNome.trim(), emailUsuario);
    }

    public Optional<Cliente> findByIdAndCreatedBy(Integer id, String emailUsuario) {
	return repository.findByIdAndCreatedBy(id, emailUsuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvarAlteracao(Cliente clienteTemp, String emailUsuario) throws BridgeException {

	Cliente cliente = findByIdAndCreatedBy(clienteTemp.getId(), emailUsuario).get();

	validarNome(clienteTemp.getNome(), cliente.getNome(), emailUsuario);

	cliente.setNome(clienteTemp.getNome());
	cliente.setObservacoes(clienteTemp.getObservacoes());

	repository.save(cliente);
    }

    private void validarNome(String nomeNovo, String nomeOriginal, String emailUsuario) throws BridgeException {
	if (nomeNovo == null || nomeNovo.isBlank())
	    throw new BridgeException("Parece que você esqueceu de preencher o nome do cliente. "
				      + "Por favor, preencha este campo para continuar.");

	// Verificando se ocorreu mudança no nome.
	if (!nomeNovo.equalsIgnoreCase(nomeOriginal))
	    verificarSeNomeJaEstaEmUso(nomeNovo, emailUsuario);
    }

    private void verificarSeNomeJaEstaEmUso(String nome, String emailUsuario) throws BridgeException {
	if (findByNomeAndCreatedBy(nome, emailUsuario) != null)
	    throw new BridgeException("Já existe um cliente com esse nome. Por favor, escolha outro nome.");
    }
}
