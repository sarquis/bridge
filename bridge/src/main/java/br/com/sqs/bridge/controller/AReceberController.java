package br.com.sqs.bridge.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sqs.bridge.model.entity.AReceber;
import br.com.sqs.bridge.model.entity.Cliente;
import br.com.sqs.bridge.service.AReceberService;
import br.com.sqs.bridge.service.ClienteService;
import br.com.sqs.bridge.util.BridgeException;
import br.com.sqs.bridge.util.BridgeMessage;

@Controller
@RequestMapping("/aReceber")
public class AReceberController {

    private static final String BASE_PATH = "aReceber/aReceber";

    private AReceberService aReceberService;
    private ClienteService clienteService;
    private BridgeMessage message;

    // Para mostrar label: Escolha um cliente
    private static final String SELECT_KEY_LABEL_NOVO_CLIENTE = "escolha_cliente_label";

    public AReceberController(AReceberService aReceberService, ClienteService clienteService, BridgeMessage message) {
	this.aReceberService = aReceberService;
	this.clienteService = clienteService;
	this.message = message;
    }

    @GetMapping("/list")
    public String list(Model model, Authentication authentication) {
	model.addAttribute("aReceberLista", aReceberService.findByCreatedByWithCliente(authentication.getName()));
	model.addAttribute("searchValue", "");
	return BASE_PATH + "-list";
    }

    @GetMapping("/listSearch")
    public String listSearch(Model model, @RequestParam("searchValue") String searchValue,
	    Authentication authentication) {
	model.addAttribute("aReceberLista", aReceberService
		.findByCreatedByAndClienteNomeContainingWithCliente(searchValue, authentication.getName()));
	model.addAttribute("searchValue", searchValue);
	return BASE_PATH + "-list";
    }

    @GetMapping("/novo")
    public String novo(Model model, Authentication authentication) {
	AReceber aReceber = new AReceber();
	aReceber.setCliente(new Cliente());

	List<Cliente> clientes = clienteService.findByCreatedByOrderByNome(authentication.getName());

	if (clientes.isEmpty())
	    permitirDigitarNomeDoCliente(model, aReceber);
	else
	    aReceber.getCliente().setNome(SELECT_KEY_LABEL_NOVO_CLIENTE);

	model.addAttribute("aReceber", aReceber);
	model.addAttribute("clientes", clientes);
	return BASE_PATH + "-novo";
    }

    @GetMapping("/editar")
    public String editar(Model model, @RequestParam("id") long id, Authentication authentication) {
	model.addAttribute("aReceber",
		aReceberService.findByIdAndCreatedByWithCliente(id, authentication.getName()).get());
	return BASE_PATH + "-editar";
    }

    @PostMapping("/salvarObservacoes")
    public String salvarObservacoes(Model model, @ModelAttribute("aReceber") AReceber aReceber,
	    Authentication authentication) {
	try {
	    aReceberService.salvarObservacoes(aReceber, authentication.getName());
	    return "redirect:../aReceber/list";
	} catch (Exception e) {
	    model.addAttribute("aReceber",
		    aReceberService.findByIdAndCreatedByWithCliente(aReceber.getId(), authentication.getName()).get());
	    model.addAttribute("message", message.handleExepection(e));
	    return BASE_PATH + "-editar";
	}
    }

    @PostMapping("/salvar")
    public String salvar(Model model, @ModelAttribute("aReceber") AReceber aReceber, Authentication authentication) {
	try {

	    if (semCliente(aReceber)) {
		aReceber.setCliente(new Cliente());
		aReceber.getCliente().setNome(SELECT_KEY_LABEL_NOVO_CLIENTE);
		throw new BridgeException("Por favor, informe um cliente.");
	    }

	    /*
	     * Controle de mudança do componte de tela <select> para <input>, permitindo o
	     * cliente digitar o nome de um novo cliente.
	     */
	    if (aReceber.getCliente().getNome().equalsIgnoreCase("novo_cli_option_value")) {
		permitirDigitarNomeDoCliente(model, aReceber);
		return BASE_PATH + "-novo";
	    }

	    aReceberService.salvarNovo(aReceber, authentication.getName());
	    return "redirect:../aReceber/list";
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	    return BASE_PATH + "-novo";
	}
    }

    @GetMapping("/excluir/{id}")
    public String excluir(Model model, @PathVariable("id") long id, Authentication authentication) {
	try {
	    aReceberService.excluir(id, authentication.getName());
	    return "redirect:../list";
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	    return BASE_PATH + "-editar";
	}
    }

    private void permitirDigitarNomeDoCliente(Model model, AReceber aReceber) {
	aReceber.getCliente().setNome("");
	model.addAttribute("permitirDigitarNomeDoCliente", true);
    }

    private boolean semCliente(AReceber aReceber) {
	return (aReceber.getCliente() == null
		|| aReceber.getCliente().getNome() == null
		|| aReceber.getCliente().getNome().isBlank());
    }
}
