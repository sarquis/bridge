package br.com.sqs.bridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sqs.bridge.model.entity.Usuario;
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
    public String list(Model model) {
	model.addAttribute("usuarios", service.findAll());
	model.addAttribute("searchValue", "");
	return "usuarios/usuarios-list";
    }

    @GetMapping("/listSearch")
    public String listSearch(Model model, @RequestParam("searchValue") String searchValue) {
	model.addAttribute("usuarios", service.findByEmailContaining(searchValue));
	model.addAttribute("searchValue", searchValue);
	return "usuarios/usuarios-list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
	model.addAttribute("usuario", new Usuario());
	return "usuarios/usuario-novo";
    }

    @GetMapping("/editar")
    public String editar(Model model, @RequestParam("usuarioId") int id) {
	model.addAttribute("usuario", service.findById(id).get());
	return "usuarios/usuario-editar";
    }

    @PostMapping("/salvar")
    public String salvar(Model model, @ModelAttribute("usuario") Usuario usuario) {
	try {
	    /*
	     * ATENÇÃO: Não usei DTO, pois aqui temos um novo registro. É recomendado sempre
	     * usar DTO quando transferir dados entre a camada de controle (Controller) e a
	     * camada de visualização (View). (Proteção contra Over-Posting.)
	     */
	    service.salvarNovoUsuario(usuario);
	    return "redirect:../usuarios/list";
	} catch (Exception e) {
	    model.addAttribute("usuario", usuario);
	    model.addAttribute("message", message.handleExepection(e));
	    return "usuarios/usuario-novo";
	}
    }

    @PostMapping("/ativarDesativar")
    public String ativarDesativar(Model model, @ModelAttribute("usuario") Usuario usuario) {
	Integer id = usuario.getId();
	service.ativarDesativar(id);
	model.addAttribute("usuario", service.findById(id).get());
	return "usuarios/usuario-editar";
    }

}
