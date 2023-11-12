package br.com.sqs.bridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sqs.bridge.entity.Usuario;
import br.com.sqs.bridge.service.UsuarioService;
import br.com.sqs.bridge.util.BridgeMessage;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService service;
    private BridgeMessage message;

    public UsuarioController(UsuarioService service, BridgeMessage message) {
	this.service = service;
	this.message = message;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
	model.addAttribute("usuarios", service.findAll());
	return "usuarios/usuarios-list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
	model.addAttribute("usuario", new Usuario());
	return "usuarios/usuario-form";
    }

    @PostMapping("/salvar")
    public String salvar(Model model, @ModelAttribute("usuario") Usuario usuario) {
	try {
	    service.save(usuario);
	} catch (Exception e) {
	    model.addAttribute("usuario", usuario);
	    model.addAttribute("message", message.handleExepection(e));
	    return "usuarios/usuario-form";
	}
	return "redirect:../usuarios/list";
    }

}
