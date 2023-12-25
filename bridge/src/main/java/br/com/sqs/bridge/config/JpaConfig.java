package br.com.sqs.bridge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(modifyOnCreate = false)
public class JpaConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
	return new BridgeAuditorAware();
    }
}
