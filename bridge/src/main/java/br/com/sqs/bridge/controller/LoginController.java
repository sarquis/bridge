package br.com.sqs.bridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.sqs.bridge.model.entity.Usuario;
import br.com.sqs.bridge.service.UsuarioService;
import br.com.sqs.bridge.util.BridgeMessage;

@Controller
public class LoginController {

    private UsuarioService service;
    private BridgeMessage message;

    public LoginController(UsuarioService service, BridgeMessage message) {
	this.service = service;
	this.message = message;
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage() {
	return "login";
    }

    @GetMapping("/showRegisterPage")
    public String showRegisterPage(Model model) {
	model.addAttribute("usuario", new Usuario());
	return "register";
    }

    @GetMapping("/showEsqueceuSenha")
    public String showEsqueceuSenha(Model model) {
	model.addAttribute("usuario", new Usuario());
	return "esqueceuSenha";
    }

    @PostMapping("/criarNovaConta")
    public String criarNovaConta(Model model, @ModelAttribute Usuario usuario) {
	String msgSucesso = """
                Conta criada com sucesso! \
                Em breve, você receberá no seu e-mail (@) a senha \
                para acessar o sistema.\
                """;
	try {
	    service.salvarNovoUsuario(usuario, true);
	    model.addAttribute("contaCriadaComSucesso", true);
	    model.addAttribute("message", message.handleSuccess(msgSucesso.replace("@", usuario.getEmail())));
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	}
	return "register";
    }

    @PostMapping("/obterNovaSenha")
    public String obterNovaSenha(Model model, @ModelAttribute Usuario usuario) {
	String msgSucesso = """
                Solicitação realizada! Se o seu e-mail estiver associado a uma conta válida, \
                em breve você receberá um e-mail com uma nova senha. \
                Verifique sua caixa de entrada, incluindo a pasta de spam, \
                caso não receba a mensagem em alguns minutos.\
                """;
	try {
	    service.solicitarEnvioDeNovaSenha(usuario, true);
	    model.addAttribute("solicitacaoRealizada", true);
	    model.addAttribute("message", message.handleSuccess(msgSucesso));
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	}
	return "esqueceuSenha";
    }

    @GetMapping("/accessDenied")
    public String showAcessDenied() {
	return "accessDenied";
    }

}
