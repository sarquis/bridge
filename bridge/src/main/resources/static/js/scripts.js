/*
Função para realizar uma pesquisa no input field.

1. Faz a manipulação do evento de digitação com atraso de um segundo (1000 milis).
2. Chama a página enviando por o valor digitado.
 
O componente input precisa ter os seguintes atributos: 
	id="searchField"
	data-url=? (Exemplo: data-url="/usuarios/listSearch")
*/
function setupDynamicSearch() {
	var selector = '#searchField';
	var nomeDoParametro = 'searchValue';
	var tempoDeEspera = 1000;
	var timeout;

	// Captura do evento de digitação no campo de texto.
	$(selector).on('keyup', function() {
		// Limpa o timeout anterior
		clearTimeout(timeout);

		// Cria um novo timeout para chamar a função após 1000 milissegundos (1 segundo).
		timeout = setTimeout(function() {
			// Valor digitado no campo.
			var valorDigitado = $(selector).val();

			// Obtém a URL dinâmica do atributo data-url
			var urlBase = $(selector).data('url');

			// Construção da URL com o valor digitado como parâmetro.
			var url = urlBase + '?' + nomeDoParametro + '=' + encodeURIComponent(valorDigitado);

			// Redirecionamento para a URL construída.
			window.location.href = url;
		}, tempoDeEspera);
	});
}

function showHidePassword(index) {
	var selector = '#show_hide_password' + index;
	$(selector + ' a').on('click', function(event) {
		event.preventDefault();
		if ($(selector + ' input').attr('type') == 'text') {
			$(selector + ' input').attr('type', 'password');
			$(selector + ' #eye' + index).removeClass('bi bi-eye');
			$(selector + ' #eye' + index).addClass('bi bi-eye-slash');
		} else if ($(selector + ' input').attr('type') == 'password') {
			$(selector + ' input').attr('type', 'text');
			$(selector + ' #eye' + index).removeClass('bi bi-eye-slash');
			$(selector + ' #eye' + index).addClass('bi bi-eye');
		}
	});
}

// Aguardar até que o DOM (Document Object Model) esteja completamente carregado.
$(document).ready(function() {
	setupDynamicSearch();
	showHidePassword("");
	showHidePassword("1");
});

function exibirTermos() {
	var mensagem = `
**Termos e Condições de Uso do Software**

Bem-vindo aos Termos e Condições de Uso do nosso software. Ao utilizar nossos serviços, você concorda com os seguintes termos e condições. Recomendamos que você leia atentamente e compreenda completamente as disposições a seguir.

1. **Disponibilidade do Serviço:**
   Este software é fornecido "como está" e "conforme disponível". Não garantimos a disponibilidade contínua e ininterrupta do serviço. Nos reservamos o direito de realizar manutenções programadas e atualizações sem aviso prévio.

2. **Alterações e Encerramento:**
   A empresa reserva-se o direito de efetuar alterações no software, suspender ou encerrar o serviço a qualquer momento, sem aviso prévio. Nós nos esforçaremos para notificar os usuários sobre mudanças significativas, mas isso não é garantido.

3. **Responsabilidades do Usuário:**
   Ao utilizar nosso software, você concorda em fornecer informações precisas e atualizadas. Você é responsável por manter a confidencialidade de suas credenciais de acesso. Qualquer atividade que ocorra sob sua conta é de sua responsabilidade.

4. **Sem Garantias Expressas ou Implícitas:**
   Não oferecemos garantias expressas ou implícitas quanto à precisão, confiabilidade ou adequação do software para um propósito específico. O uso do software é por sua conta e risco.

5. **Propriedade Intelectual:**
   Todo o conteúdo e propriedade intelectual associados ao software são de propriedade exclusiva da empresa. Você concorda em não reproduzir, distribuir ou criar obras derivadas do software sem autorização.

6. **Limitação de Responsabilidade:**
   Em nenhuma circunstância a empresa será responsável por quaisquer danos diretos, indiretos, incidentais, especiais ou consequentes decorrentes do uso ou incapacidade de uso do software.

7. **Leis e Jurisdição:**
   Estes termos serão regidos e interpretados de acordo com as leis vigentes. Qualquer disputa decorrente destes termos será submetida à jurisdição exclusiva dos tribunais competentes.

Ao clicar em "Aceitar" ou utilizar nosso software, você reconhece ter lido, compreendido e concordado com estes Termos e Condições de Uso. Se não concordar com estes termos, por favor, não utilize nosso software. Estes termos podem ser alterados pela empresa a qualquer momento, e é responsabilidade do usuário revisá-los periodicamente.
  `;
	Swal.fire({
		title: 'Termos e Condições',
		html: mensagem,
		icon: null,
		showCancelButton: true,
		confirmButtonText: 'Aceitar',
		cancelButtonText: 'Cancelar',
	}).then((result) => {
		if (result.isConfirmed) {
			window.location.href = "/showRegisterPage";
		}
	});

	/* 
	Ícones disponíveis:
	'success': Ícone de sucesso (verificado)
	'error': Ícone de erro (X)
	'warning': Ícone de aviso (ponto de exclamação)
	'info': Ícone de informação (i)
	'question': Ícone de pergunta (?)
	'question-circle': Ícone de pergunta em formato de círculo
	Qualquer ícone do Font Awesome, por exemplo, 'fa fa-thumbs-up'
	*/

	/*
	  Forma padrão javascript:
	  var resposta = confirm(mensagem);
	  if (resposta) {
		  window.location.href = "/showRegisterPage"; 
	  }
	*/

}

// Para desabilitar o botão assim que for clicado e evitar cliques duplos.
function disableButton(button) {
	button.disabled = true;
	button.innerHTML = "Aguarde...";
	return true; // Allow the default behavior (i.e., navigation to the specified URL)
}

function submitForm() {
	var form = document.getElementById("form");
	form.submit();
}

function formatarMoeda(element) {
	let valor = element.value.replace(/\D/g, ''); 
	if (valor !== "") {
		valor = (parseFloat(valor) / 100).toFixed(2).replace('.', ',');
		valor = valor.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
	}
	element.value = valor;
}