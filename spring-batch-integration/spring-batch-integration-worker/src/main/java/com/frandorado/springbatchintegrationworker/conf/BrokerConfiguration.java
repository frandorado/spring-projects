package com.frandorado.springbatchintegrationworker.conf;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frandorado.springbatchintegrationworker.property.ApplicationProperties;

@Configuration
public class BrokerConfiguration {
    
    @Autowired
    ApplicationProperties applicationProperties;
    
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(applicationProperties.getBrokerUrl());
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }
    
    
}
