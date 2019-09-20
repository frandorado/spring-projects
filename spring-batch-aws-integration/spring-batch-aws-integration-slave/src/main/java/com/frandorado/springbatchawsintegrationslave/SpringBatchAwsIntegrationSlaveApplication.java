package com.frandorado.springbatchawsintegrationslave;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableBatchProcessing
@EnableIntegration
@ComponentScan(basePackages = "com.frandorado")
public class SpringBatchAwsIntegrationSlaveApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBatchAwsIntegrationSlaveApplication.class, args);
    }
    
}
