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

import br.com.sqs.bridge.model.entity.Cliente;
import br.com.sqs.bridge.model.entity.Pagamento;
import br.com.sqs.bridge.service.ClienteService;
import br.com.sqs.bridge.service.PagamentoService;
import br.com.sqs.bridge.util.BridgeException;
import br.com.sqs.bridge.util.BridgeMessage;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

    private static final String BASE_PATH = "pagamentos/pagamento";

    private PagamentoService pagamentoService;
    private ClienteService clienteService;
    private BridgeMessage message;

    // Para mostrar label: Escolha um cliente
    private static final String SELECT_KEY_LABEL_NOVO_CLIENTE = "escolha_cliente_label";

    public PagamentoController(PagamentoService pagamentoService, ClienteService clienteService,
	    BridgeMessage message) {
	this.pagamentoService = pagamentoService;
	this.clienteService = clienteService;
	this.message = message;
    }

    @GetMapping("/list")
    public String list(Model model, Authentication authUser) {
	model.addAttribute("pagamentos", pagamentoService.findByCreatedByWithCliente(authUser.getName()));
	model.addAttribute("searchValue", "");
	return BASE_PATH + "-list";
    }

    @GetMapping("/listSearch")
    public String listSearch(Model model, @RequestParam("searchValue") String searchValue, Authentication authUser) {
	model.addAttribute("pagamentos", pagamentoService
		.findByCreatedByAndClienteNomeContainingWithCliente(searchValue, authUser.getName()));
	model.addAttribute("searchValue", searchValue);
	return BASE_PATH + "-list";
    }

    @GetMapping("/novo")
    public String novo(Model model, Authentication authUser) {

	List<Cliente> clientes = clienteService.findByCreatedByOrderByNome(authUser.getName());

	if (clientes.isEmpty()) {
	    model.addAttribute("pagamentos", pagamentoService.findByCreatedByWithCliente(authUser.getName()));
	    model.addAttribute("searchValue", "");
	    BridgeException exception = new BridgeException("Desculpe, não é possível realizar esta ação no momento, "
							    + "pois não há clientes cadastrados para receber. "
							    + "Por favor, cadastre pelo menos um cliente antes de prosseguir.");
	    model.addAttribute("message", message.handleExepection(exception));
	    return BASE_PATH + "-list";
	}

	Pagamento pagamento = new Pagamento();
	pagamento.setCliente(new Cliente());
	pagamento.getCliente().setNome(SELECT_KEY_LABEL_NOVO_CLIENTE);

	model.addAttribute("pagamento", pagamento);
	model.addAttribute("clientes", clientes);

	return BASE_PATH + "-novo";
    }

    @GetMapping("/editar")
    public String editar(Model model, @RequestParam("id") long id, Authentication authUser) {
	model.addAttribute("pagamento", pagamentoService.findByIdAndCreatedByWithCliente(id, authUser.getName()).get());
	return BASE_PATH + "-editar";
    }

    @PostMapping("/salvarObservacoes")
    public String salvarObservacoes(Model model, @ModelAttribute("pagamento") Pagamento pagamento,
	    Authentication authUser) {
	try {
	    pagamentoService.salvarObservacoes(pagamento, authUser.getName());
	    return "redirect:../pagamentos/list";
	} catch (Exception e) {
	    model.addAttribute("pagamento", pagamento);
	    model.addAttribute("message", message.handleExepection(e));
	    return BASE_PATH + "-editar";
	}
    }

    @PostMapping("/salvar")
    public String salvar(Model model, @ModelAttribute("pagamento") Pagamento pagamento, Authentication authUser) {
	try {
	    pagamentoService.salvarNovo(pagamento, authUser.getName());
	    return "redirect:../pagamentos/list";
	} catch (Exception e) {
	    List<Cliente> clientes = clienteService.findByCreatedByOrderByNome(authUser.getName());
	    model.addAttribute("clientes", clientes);
	    pagamento.setCliente(new Cliente());
	    pagamento.getCliente().setNome(SELECT_KEY_LABEL_NOVO_CLIENTE);
	    model.addAttribute("pagamento", pagamento);
	    model.addAttribute("message", message.handleExepection(e));
	    return BASE_PATH + "-novo";
	}
    }

    @GetMapping("/excluir/{id}")
    public String excluir(Model model, @PathVariable("id") long id, Authentication authUser) {
	try {
	    pagamentoService.excluir(id, authUser.getName());
	    return "redirect:../list";
	} catch (Exception e) {
	    model.addAttribute("message", message.handleExepection(e));
	    return BASE_PATH + "-editar";
	}
    }

}
