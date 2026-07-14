package dev.muzakkitlabs.java_ai_playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JavaAiPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaAiPlaygroundApplication.class, args);
	}
	
}