package com.frandorado.springbatchintegrationmaster.step.chunking;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.integration.chunk.ChunkMessageChannelItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

/**
 * Use of remote chunking
 */
@Configuration
public class StepOneConfiguration {
    
    private static final String REQUEST_CHANNEL = "stepOneRequestChannel";
    private static final String RESPONSE_CHANNEL = "stepOneResponseChannel";
    private static final Integer CHUNK_SIZE = 1;
    
    @Bean
    public Step stepOne(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("stepOne")
                .<Integer, Integer>chunk(CHUNK_SIZE)
                .reader(stepOneItemReader())
                .writer(stepOneItemWriter())
                .build();
    }
    
    @Bean
    public ListItemReader<Integer> stepOneItemReader() {
        List<Integer> range = IntStream.rangeClosed(1, 50)
                .boxed().collect(Collectors.toList());
        return new ListItemReader<>(range);
    }
    
    @Bean
    public ChunkMessageChannelItemWriter<Integer> stepOneItemWriter() {
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        messagingTemplate.setDefaultChannel(stepOneRequestChannel());
        ChunkMessageChannelItemWriter<Integer> chunkMessageChannelItemWriter
                = new ChunkMessageChannelItemWriter<>();
        chunkMessageChannelItemWriter.setMessagingOperations(messagingTemplate);
        chunkMessageChannelItemWriter.setReplyChannel(stepOneResponseChannel());
        return chunkMessageChannelItemWriter;
    }
    
    @Bean(name = REQUEST_CHANNEL)
    public DirectChannel stepOneRequestChannel() {
        return new DirectChannel();
    }
    
    @Bean(name = RESPONSE_CHANNEL)
    public QueueChannel stepOneResponseChannel() {
        return new QueueChannel();
    }
    
    @Bean
    public IntegrationFlow stepOneRequestIntegrationFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(stepOneRequestChannel())
                .handle(Jms.outboundAdapter(connectionFactory).destination(REQUEST_CHANNEL))
                .get();
    }
    
    @Bean
    public IntegrationFlow stepOneResponseIntegrationFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(connectionFactory).destination(RESPONSE_CHANNEL))
                .channel(stepOneResponseChannel())
                .get();
    }
    
}
