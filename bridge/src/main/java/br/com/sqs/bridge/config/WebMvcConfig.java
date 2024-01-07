package br.com.sqs.bridge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.sqs.bridge.util.BigDecimalFormatter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
	registry.addFormatter(new BigDecimalFormatter());
    }

}
