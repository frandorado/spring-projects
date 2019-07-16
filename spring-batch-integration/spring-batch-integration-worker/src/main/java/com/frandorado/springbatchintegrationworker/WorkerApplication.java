package com.frandorado.springbatchintegrationworker;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableBatchProcessing
@EnableIntegration
@EnableBatchIntegration
public class WorkerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }
    
}
