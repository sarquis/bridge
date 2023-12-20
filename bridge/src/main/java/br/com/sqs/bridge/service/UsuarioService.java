package br.com.sqs.bridge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sqs.bridge.model.entity.Usuario;
import br.com.sqs.bridge.repository.UsuarioRepository;
import br.com.sqs.bridge.util.BridgeEmailValidator;
import br.com.sqs.bridge.util.BridgeException;
import br.com.sqs.bridge.util.BridgePasswordHandler;

@Service
public class UsuarioService {

    private UsuarioRepository repository;
    private PasswordEncoder passwordEncoder;
    private FuncaoService funcaoService;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder, FuncaoService funcaoService) {
	this.repository = repository;
	this.passwordEncoder = passwordEncoder;
	this.funcaoService = funcaoService;
    }

    public List<Usuario> findAll() {
	return repository.findAllByOrderByIdDesc();
    }

    public List<Usuario> findByEmailContaining(String email) {
	if (email == null || email.isBlank())
	    return repository.findAllByOrderByIdDesc();

	return repository.findByEmailContainingOrderByEmailAsc(email.trim());
    }

    public Optional<Usuario> findById(Integer id) {
	return repository.findById(id);
    }

    @Transactional
    public void salvarNovoUsuario(Usuario usuario, boolean viaTelaDeRegistro) throws BridgeException {

	if (!BridgeEmailValidator.isValidEmail(usuario.getEmail()))
	    throw new BridgeException(
		    "O endereço de e-mail inserido não é válido. Por favor, verifique e tente novamente.");

	usuario.setId(null);
	usuario.setAtivo(!viaTelaDeRegistro);
	String password = viaTelaDeRegistro
		? BridgePasswordHandler.generateInitialPassword()
		: passwordEncoder.encode(usuario.getSenha());
	usuario.setSenha(password);
	usuario.setFuncoes(funcaoService.obterFuncaoPadrao());
	repository.save(usuario);
    }

    @Transactional
    public void ativarDesativar(Integer idUsuario) {
	Usuario usuario = repository.findById(idUsuario).get();
	usuario.setAtivo(!usuario.getAtivo());
	repository.save(usuario);
    }

    @Transactional
    public void alterarSenha(String senhaAtual, String novaSenha, String userEmail) throws BridgeException {
	Usuario usuario = repository.findByEmail(userEmail);

	if (!passwordEncoder.matches(senhaAtual, usuario.getSenha()))
	    throw new BridgeException("Senha atual inválida");

	if (!BridgePasswordHandler.isPasswordSecure(novaSenha))
	    throw new BridgeException("A nova senha não atende aos requisitos mínimos de segurança.");

	usuario.setSenha(passwordEncoder.encode(novaSenha));
	repository.save(usuario);
    }

}
