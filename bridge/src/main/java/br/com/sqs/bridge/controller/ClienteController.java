package br.com.sqs.bridge.controller;

import java.math.BigDecimal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sqs.bridge.model.entity.Cliente;
import br.com.sqs.bridge.service.ClienteService;
import br.com.sqs.bridge.util.BridgeMessage;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private static final String BASE_PATH = "clientes/cliente";

    private ClienteService service;
    private BridgeMessage message;

    public ClienteController(ClienteService service, BridgeMessage message) {
	this.service = service;
	this.message = message;
    }

    @GetMapping("/list")
    public String list(Model model, Authentication authUser) {
	model.addAttribute("clientes", service.findByCreatedByOrderByNome(authUser.getName()));
	model.addAttribute("searchValue", "");
	return BASE_PATH + "-list";
    }

    @GetMapping("/listSearch")
    public String listSearch(Model model, @RequestParam("searchValue") String searchValue,
	    Authentication authUser) {
	model.addAttribute("clientes", service
		.findByNomeContainingAndCreatedByOrderByNomeAsc(searchValue, authUser.getName()));
	model.addAttribute("searchValue", searchValue);
	return BASE_PATH + "-list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
	Cliente cliente = new Cliente();
	cliente.setSaldo(BigDecimal.ZERO);
	model.addAttribute("cliente", cliente);
	return BASE_PATH + "-novo";
    }

    @GetMapping("/editar")
    public String editar(Model model, @RequestParam("id") int id, Authentication authUser) {
	service.recalcularSaldo(id, authUser.getName(), true);
	model.addAttribute("cliente", service.findByIdAndCreatedBy(id, authUser.getName()).get());
	return BASE_PATH + "-editar";
    }

    @GetMapping("/recalcularSaldo")
    public String recalcularSaldo(Model model, @RequestParam("id") int id, Authentication authUser) {
	String saldo = service.recalcularSaldo(id, authUser.getName(), false);
	model.addAttribute("cliente", service.findByIdAndCreatedBy(id, authUser.getName()).get());
	model.addAttribute("message", message.handleSuccess("Saldo do cliente: " + saldo));
	return BASE_PATH + "-editar";
    }

    @PostMapping("/salvarAlteracao")
    public String salvarAlteracao(Model model, @ModelAttribute("cliente") Cliente cliente, Authentication authUser) {
	try {
	    service.salvarAlteracao(cliente, authUser.getName());
	    return "redirect:../clientes/list";
	} catch (Exception e) {
	    model.addAttribute("cliente", cliente);
	    model.addAttribute("message", message.handleExepection(e));
	    return BASE_PATH + "-editar";
	}
    }

    @PostMapping("/salvar")
    public String salvar(Model model, @ModelAttribute("cliente") Cliente cliente, Authentication authUser) {
	try {
	    cliente.setSaldo(BigDecimal.ZERO);
	    service.salvarNovoCliente(cliente, authUser.getName());
	    return "redirect:../clientes/list";
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	    return BASE_PATH + "-novo";
	}
    }

    @GetMapping("/excluir")
    public String excluir(Model model, @RequestParam("id") int id, Authentication authUser) {
	try {
	    service.excluir(id, authUser.getName());
	    return "redirect:../clientes/list";
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	    model.addAttribute("cliente", service.findByIdAndCreatedBy(id, authUser.getName()).get());
	    return BASE_PATH + "-editar";
	}
    }

}
