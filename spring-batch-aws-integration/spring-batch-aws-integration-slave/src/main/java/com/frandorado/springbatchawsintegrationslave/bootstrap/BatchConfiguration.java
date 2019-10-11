package com.frandorado.springbatchawsintegrationslave.bootstrap;

import org.springframework.batch.integration.partition.BeanFactoryStepLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {
    
    @Bean
    public BeanFactoryStepLocator beanFactoryStepLocator() {
        return new BeanFactoryStepLocator();
    }
}
