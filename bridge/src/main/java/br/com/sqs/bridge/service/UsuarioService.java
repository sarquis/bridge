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
    private MailSenderService mailSenderService;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder, FuncaoService funcaoService,
	    MailSenderService mailSenderService) {
	this.repository = repository;
	this.passwordEncoder = passwordEncoder;
	this.funcaoService = funcaoService;
	this.mailSenderService = mailSenderService;
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

	/*
	 * Quando o usuário realizar o cadastro por meio da Tela de Registro
	 * ("Criar Nova Conta"), ele será inicialmente registrado como inativo, e uma
	 * senha será gerada automaticamente. A ativação da conta e o envio da nova
	 * senha serão de responsabilidade do administrador.
	 */

	usuario.setId(null);
	usuario.setAtivo(!viaTelaDeRegistro);
	String password = viaTelaDeRegistro
		? passwordEncoder.encode(BridgePasswordHandler.generateInitialPassword())
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

    public void enviarNovaSenha(Usuario usuario, boolean esqueciMinhaSenha) {

	final String novaSenha = criarSalvarNovaSenha(usuario);

	String subject = esqueciMinhaSenha ? "橋 Bridge - 2Receive - nova senha" : "Bem-vindo ao 橋 Bridge - 2Receive!";
	String body = "";

	if (esqueciMinhaSenha) {
	    body += "Conforme solicitado, aqui está sua nova senha para acessar nossa plataforma:\r\n";
	} else {
	    body += "Seja muito bem-vindo ao 2Receive, sua plataforma dedicada para uma gestão eficiente e simplificada!\r\n\r\n";
	    body += "Aqui está sua senha para acessar nossa plataforma:\r\n";
	}

	body += "Senha: " + novaSenha + "\r\n\r\n";
	body += "Recomendamos que altere sua senha assim que possível para garantir a segurança de sua conta.\r\n\r\n";

	if (!esqueciMinhaSenha)
	    body += "Agradecemos por escolher o Bridge - 2Receive. Esperamos que aproveite ao máximo nossa plataforma!\r\n\r\n";

	body += "Atenciosamente,\r\n";
	body += "Equipe 橋 Bridge - 2Receive\r\n";

	mailSenderService.sendNewMail(usuario.getEmail(), subject, body);
    }

    @Transactional
    private String criarSalvarNovaSenha(Usuario usuario) {
	final String novaSenha = BridgePasswordHandler.generateInitialPassword();
	usuario.setSenha(passwordEncoder.encode(novaSenha));
	repository.save(usuario);
	return novaSenha;
    }

}
