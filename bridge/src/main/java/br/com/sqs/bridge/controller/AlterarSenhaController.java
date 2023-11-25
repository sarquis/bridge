package br.com.sqs.bridge.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.sqs.bridge.model.dto.SenhaDTO;
import br.com.sqs.bridge.service.UsuarioService;
import br.com.sqs.bridge.util.BridgeMessage;

@Controller
public class AlterarSenhaController {

    private UsuarioService service;
    private BridgeMessage message;

    public AlterarSenhaController(UsuarioService service, BridgeMessage message) {
	this.service = service;
	this.message = message;
    }

    @GetMapping("/alterarSenha")
    public String showAlterarSenha(Model model) {
	model.addAttribute("senhaDTO", new SenhaDTO());
	return "alterarSenha";
    }

    @PostMapping("/salvar")
    public String salvar(Model model, @ModelAttribute("senhaDTO") SenhaDTO senhaDTO,
	    @AuthenticationPrincipal UserDetails userDetails) {
	try {
	    String username = userDetails.getUsername();
	    System.out.println(username);
	    // Integer idUsuario = null;
	    // service.alterarSenha(senhaDTO.getAtual(), senhaDTO.getNova(), idUsuario); //
	    // TODO CONTINUAR AQUI TENHO QUE PEGAR O NOME DO USUARIO E TRANSFORMAR EM ID.
	    model.addAttribute("message", message.handleSuccess("Senha alterada com sucesso!"));
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	}
	model.addAttribute("senhaDTO", new SenhaDTO());
	return "alterarSenha";
    }

}
