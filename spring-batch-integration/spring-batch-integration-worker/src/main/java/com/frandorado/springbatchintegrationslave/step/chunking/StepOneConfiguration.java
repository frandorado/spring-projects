package com.frandorado.springbatchintegrationslave.step.chunking;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.step.item.ChunkProcessor;
import org.springframework.batch.core.step.item.SimpleChunkProcessor;
import org.springframework.batch.integration.chunk.ChunkProcessorChunkHandler;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;

import com.frandorado.springbatchintegrationslave.step.chunking.RemoteChunkingStepConfiguration;

// Step1
@Configuration
public class StepOneConfiguration extends RemoteChunkingStepConfiguration {
    
    /*private static final String REQUEST_CHANNEL = "stepOneRequestChannel";
    private static final String RESPONSE_CHANNEL = "stepOneResponseChannel";
    
    @Bean
    public ItemProcessor<Integer, Integer> itemProcessor() {
        return item -> {
            System.out.println("processing item " + item);
            
            return item;
        };
    }
    
    @Bean
    public ItemWriter<Integer> itemWriter() {
        return items -> {
            for (Integer item : items) {
                System.out.println("writing item " + item);
            }
        };
    }
    
    // Channels
    @Bean(name = REQUEST_CHANNEL)
    public DirectChannel stepOneRequestChannel() {
        return buildInputDirectChannel();
    }
    
    @Bean(name = RESPONSE_CHANNEL)
    public DirectChannel stepOneResponseChannel() {
        return buildOutputDirectChannel();
    }
    
    // Queue
    @Bean
    public IntegrationFlow stepOneRequestIntegrationFlow() {
        return buildInputIntegrationFlow(REQUEST_CHANNEL, stepOneRequestChannel());
    }
    
    @Bean
    public IntegrationFlow stepOneOutputIntegrationFlow(ActiveMQConnectionFactory connectionFactory) {
        return buildOutputIntegrationFlow(RESPONSE_CHANNEL, stepOneResponseChannel());
    }
    
    // Service activator
    @Bean
    @ServiceActivator(inputChannel = REQUEST_CHANNEL, outputChannel = RESPONSE_CHANNEL)
    public ChunkProcessorChunkHandler<Integer> chunkProcessorChunkHandler() {
        ChunkProcessor<Integer> chunkProcessor = new SimpleChunkProcessor<>(itemProcessor(), itemWriter());
        ChunkProcessorChunkHandler<Integer> chunkProcessorChunkHandler = new ChunkProcessorChunkHandler<>();
        chunkProcessorChunkHandler.setChunkProcessor(chunkProcessor);
        return chunkProcessorChunkHandler;
    }
    
     */
}
