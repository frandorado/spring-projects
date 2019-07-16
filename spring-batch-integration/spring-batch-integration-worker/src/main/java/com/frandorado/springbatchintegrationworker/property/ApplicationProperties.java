package com.frandorado.springbatchintegrationworker.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class ApplicationProperties {
    
    @Value("${broker.url}")
    private String brokerUrl;
    
}
