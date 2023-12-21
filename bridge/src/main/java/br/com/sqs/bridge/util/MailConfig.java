package br.com.sqs.bridge.util;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    JavaMailSender getJavaMailSender() {
	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	mailSender.setHost("smtp.gmail.com");
	mailSender.setPort(587);

	mailSender.setUsername("bridge2receive@gmail.com");
	mailSender.setPassword("jjdc zqxa volc time");

	Properties props = mailSender.getJavaMailProperties();
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.debug", "true");

	return mailSender;
    }
}