package br.com.sqs.bridge.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sqs.bridge.model.dto.SenhaDTO;
import br.com.sqs.bridge.service.UsuarioService;
import br.com.sqs.bridge.util.BridgeMessage;

@Controller
@RequestMapping("/alterarSenha")
public class AlterarSenhaController {

    private UsuarioService service;
    private BridgeMessage message;

    public AlterarSenhaController(UsuarioService service, BridgeMessage message) {
	this.service = service;
	this.message = message;
    }

    @GetMapping("/show")
    public String showAlterarSenha(Model model) {
	model.addAttribute("senhaDTO", new SenhaDTO());
	return "alterarSenha";
    }

    @PostMapping("/salvar")
    public String salvar(Model model, @ModelAttribute SenhaDTO senhaDTO,
	    @AuthenticationPrincipal UserDetails userDetails) {
	try {
	    String userName = userDetails.getUsername();
	    service.alterarSenha(senhaDTO.getAtual(), senhaDTO.getNova(), userName);
	    model.addAttribute("senhaAlteradaComSucesso", true);
	    model.addAttribute("message", message.handleSuccess("Senha alterada com sucesso!"));
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	}
	model.addAttribute("senhaDTO", new SenhaDTO());
	return "alterarSenha";
    }

}
