package br.com.sqs.bridge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sqs.bridge.entity.Usuario;
import br.com.sqs.bridge.repository.UsuarioRepository;

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

    public Optional<Usuario> findById(Integer id) {
	return repository.findById(id);
    }

    @Transactional
    public void save(Usuario usuario) {
	if (usuario.getId() == null) {
	    usuario.setAtivo(true);
	    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
	    usuario.setFuncoes(funcaoService.obterFuncaoPadrao());
	}
	repository.save(usuario);
    }

}
