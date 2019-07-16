package com.frandorado.springbatchintegrationmaster;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableIntegration
@EnableBatchIntegration
@EnableScheduling
public class MasterApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MasterApplication.class, args);
    }
    
}
