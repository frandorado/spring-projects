package com.frandorado.springbatchawsintegrationmaster;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableIntegration
@ComponentScan(basePackages = "com.frandorado")
public class SpringBatchAwsIntegrationMasterApplication {

	public static void main(String[] args) {
		configureSpringBatchProperties();
		SpringApplication.run(SpringBatchAwsIntegrationMasterApplication.class, args);
	}
	
	private static void configureSpringBatchProperties() {
		System.setProperty("spring.batch.job.enabled", "true");
		System.setProperty("spring.batch.initialize-schema", "always");
	}

}
