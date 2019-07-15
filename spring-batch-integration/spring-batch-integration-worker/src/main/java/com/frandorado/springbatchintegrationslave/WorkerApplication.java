package com.frandorado.springbatchintegrationslave;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableBatchProcessing
@EnableIntegration
public class WorkerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }
    
}
