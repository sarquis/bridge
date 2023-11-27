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
			$(selector + ' #eye'+ index).removeClass('bi bi-eye');
			$(selector + ' #eye'+ index).addClass('bi bi-eye-slash');
		} else if ($(selector + ' input').attr('type') == 'password') {
			$(selector + ' input').attr('type', 'text');
			$(selector + ' #eye'+ index).removeClass('bi bi-eye-slash');
			$(selector + ' #eye'+ index).addClass('bi bi-eye');
		}
	});
}

// Aguardar até que o DOM (Document Object Model) esteja completamente carregado.
$(document).ready(function() {
	setupDynamicSearch();
	showHidePassword("");
	showHidePassword("1");
});



