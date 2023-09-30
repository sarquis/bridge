package br.com.sqs.bridge.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
	JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
	jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT user_id, pw, active FROM members WHERE user_id=?");
	jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT user_id, role FROM roles WHERE user_id=?");
	return jdbcUserDetailsManager;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	http.authorizeHttpRequests(configurer -> configurer
		.requestMatchers("/").hasRole("EMPLOYEE")
		.requestMatchers("/leaders/**").hasRole("MANAGER")
		.requestMatchers("/systems/**").hasRole("ADMIN")
		.anyRequest().authenticated())
		.formLogin(form -> form
			.loginPage("/showMyLoginPage")
			.loginProcessingUrl("/authenticateTheUser")
			.permitAll())
		.logout(logout -> logout.permitAll())
		.exceptionHandling(configurer -> configurer
			.accessDeniedPage("/access-denied"));

	return http.build();
    }

}
