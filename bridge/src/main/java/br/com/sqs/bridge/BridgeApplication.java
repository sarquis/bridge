package br.com.sqs.bridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class BridgeApplication {

    public static void main(String[] args) {
	System.out.println("TESTE");
	// SpringApplication.run(BridgeApplication.class, args);
	SpringApplication app = new SpringApplication(BridgeApplication.class);

	ConfigurableApplicationContext context = app.run(args);
	ConfigurableEnvironment env = context.getEnvironment();

	String value1 = env.getProperty("spring.datasource.url");
	String value2 = env.getProperty("spring.datasource.username");
	String value3 = env.getProperty("spring.datasource.password");

	System.out.println("Value 1: " + value1);
	System.out.println("Value 2: " + value2);
	System.out.println("Value 3: " + value3);

	System.exit(0);
    }

}
