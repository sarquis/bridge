package br.com.sqs.bridge.service;

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

    public List<Cliente> obterTodosOsClientes(String emailUsuario) {
	return repository.findByCreatedByOrderByNome(emailUsuario);
    }

    public Cliente obterClienteEspecifico(String nome, String emailUsuario) {
	return repository.findByNomeAndCreatedBy(nome, emailUsuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public void salvarNovoCliente(Cliente cliente) {
	cliente.setId(null);
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

    @Transactional(rollbackFor = Exception.class)
    public void salvarAlteracao(Cliente clienteTemp, String emailUsuario) throws BridgeException {

	Cliente cliente = findById(clienteTemp.getId(), emailUsuario).get();

	validarNome(clienteTemp.getNome(), cliente.getNome(), emailUsuario);

	cliente.setNome(clienteTemp.getNome());
	cliente.setObservacoes(clienteTemp.getObservacoes());

	repository.save(cliente);
    }

    private void validarNome(String nomeNovo, String nomeOriginal, String emailUsuario) throws BridgeException {
	if (nomeNovo == null || nomeNovo.isBlank())
	    throw new BridgeException("Parece que você esqueceu de preencher o nome do cliente. "
				      + "Por favor, preencha este campo para continuar.");

	// Verificando se ocorreu troca no nome.
	if (!nomeNovo.equalsIgnoreCase(nomeOriginal))
	    if (obterClienteEspecifico(nomeNovo, emailUsuario) != null)
		throw new BridgeException("Já existe um cliente com esse nome. Por favor, escolha outro nome.");
    }
}
