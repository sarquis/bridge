$(document).ready(function() {
	$("#show_hide_password a").on('click', function(event) {
		event.preventDefault();
		if ($('#show_hide_password input').attr("type") == "text") {
			$('#show_hide_password input').attr('type', 'password');
			$('#show_hide_password #eye').addClass("bi bi-eye-slash");
			$('#show_hide_password #eye').removeClass("bi bi-eye");
		} else if ($('#show_hide_password input').attr("type") == "password") {
			$('#show_hide_password input').attr('type', 'text');
			$('#show_hide_password #eye').removeClass("bi bi-eye-slash");
			$('#show_hide_password #eye').addClass("bi bi-eye");
		}
	});
});