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

    @PostMapping("/criarNovaConta")
    public String criarNovaConta(Model model, @ModelAttribute("usuario") Usuario usuario) {
	String msgSucesso = "Conta criada com sucesso! "
			    + "Em breve, você receberá no seu e-mail (@) a senha "
			    + "para acessar o sistema.";
	try {
	    service.salvarNovoUsuario(usuario, true);
	    model.addAttribute("contaCriadaComSucesso", true);
	    model.addAttribute("message", message.handleSuccess(msgSucesso.replace("@", usuario.getEmail())));
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	}
	return "register";
    }

    @GetMapping("/accessDenied")
    public String showAcessDenied() {
	return "accessDenied";
    }

}
