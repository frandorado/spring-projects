package com.frandorado.springbatchawsintegrationslave.bootstrap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
public class AwsConfiguration {
    
    @Bean
    public AmazonSQSAsync amazonSQSAsync() {
        
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration("http://localstack:4576",
                "us-east-1");
        
        return AmazonSQSAsyncClientBuilder.standard().withEndpointConfiguration(endpointConfiguration).build();
    }
}
