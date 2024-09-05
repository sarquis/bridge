package br.com.sqs.bridge;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BridgeApplication {

    public static void main(String[] args) {
	TimeZone.setDefault(TimeZone.getTimeZone("America/Fortaleza"));
	SpringApplication.run(BridgeApplication.class, args);
    }

}
