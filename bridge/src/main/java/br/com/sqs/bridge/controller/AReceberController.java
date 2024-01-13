package br.com.sqs.bridge.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.sqs.bridge.model.entity.AReceber;
import br.com.sqs.bridge.model.entity.Cliente;
import br.com.sqs.bridge.service.AReceberService;
import br.com.sqs.bridge.service.ClienteService;
import br.com.sqs.bridge.util.BridgeException;
import br.com.sqs.bridge.util.BridgeMessage;

@Controller
@RequestMapping("/aReceber")
public class AReceberController {

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

    @GetMapping("/novo")
    public String novo(Model model, Authentication authentication) {
	AReceber aReceber = new AReceber();
	aReceber.setCliente(new Cliente());
	aReceber.getCliente().setNome(SELECT_KEY_LABEL_NOVO_CLIENTE);
	model.addAttribute("aReceber", aReceber);
	model.addAttribute("clientes", clienteService.obterClientes(authentication.getName()));
	return "aReceber/aReceber-novo";
    }

    @PostMapping("/salvar")
    public String salvar(Model model, @ModelAttribute("aReceber") AReceber aReceber) {
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
		aReceber.getCliente().setNome("");
		model.addAttribute("permitirDigitarNomeDoCliente", true);
		return "aReceber/aReceber-novo";
	    }

	    aReceberService.salvar(aReceber);
	    return "aReceber/aReceber-list";
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	    return "aReceber/aReceber-novo";
	}
    }

    private boolean semCliente(AReceber aReceber) {
	return (aReceber.getCliente() == null
		|| aReceber.getCliente().getNome() == null
		|| aReceber.getCliente().getNome().isBlank());
    }
}
