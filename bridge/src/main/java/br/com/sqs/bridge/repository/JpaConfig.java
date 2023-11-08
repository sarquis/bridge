package br.com.sqs.bridge.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.com.sqs.bridge.security.SpringSecurityAuditorAware;

@Configuration
@EnableJpaAuditing(modifyOnCreate = false)
public class JpaConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
	return new SpringSecurityAuditorAware();
    }
}
