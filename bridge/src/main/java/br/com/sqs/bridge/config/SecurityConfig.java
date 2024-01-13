package br.com.sqs.bridge.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
	JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
	final String sqlUser = " SELECT email, senha, ativo FROM usuario WHERE email = ? ";
	jdbcUserDetailsManager.setUsersByUsernameQuery(sqlUser);
	final String sqlRole = "        SELECT u.email, f.nome 		"
			       + "        FROM funcao f 		"
			       + "  INNER JOIN usuario_funcao uf 	"
			       + "          ON uf.funcao_id = f.id 	"
			       + "  INNER JOIN usuario u 		"
			       + "          ON u.id = uf.usuario_id 	"
			       + "         AND u.email = ? 		";
	jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(sqlRole);
	return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(configurer -> configurer
		.requestMatchers("/js/**", "/css/**", "/html/**", "/img/**",
			"/showRegisterPage", "/criarNovaConta", "/showEsqueceuSenha", "/obterNovaSenha")
		.permitAll()
		.requestMatchers("/").hasRole("USUARIO")
		.requestMatchers("/usuarios/**").hasRole("ADMIN")
		.requestMatchers("/aReceber/**").hasRole("USUARIO")
		.anyRequest().authenticated())
		.formLogin(form -> form
			.loginPage("/showLoginPage")
			.loginProcessingUrl("/authenticateTheUser") // action : form : login page
			.permitAll())
		.logout(logout -> logout.permitAll())
		.exceptionHandling(configurer -> configurer
			.accessDeniedPage("/accessDenied"));
	return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }
}
