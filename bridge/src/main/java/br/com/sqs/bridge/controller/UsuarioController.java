package br.com.sqs.bridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sqs.bridge.dao.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
	this.repository = repository;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
	model.addAttribute("usuarios", repository.findAllByOrderByIdAsc());
	return "usuarios/usuarios-list";
    }

}
