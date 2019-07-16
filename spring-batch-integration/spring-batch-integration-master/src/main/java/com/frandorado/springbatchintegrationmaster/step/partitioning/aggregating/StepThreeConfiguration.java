package com.frandorado.springbatchintegrationmaster.step.partitioning.aggregating;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.integration.partition.RemotePartitioningMasterStepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

@Configuration
public class StepThreeConfiguration {
    
    private static final int GRID_SIZE = 3;
    
    private static final String REQUEST_CHANNEL = "stepThreeRequestChannel";
    private static final String RESPONSE_CHANNEL = "stepThreeResponseChannel";
    
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    RemotePartitioningMasterStepBuilderFactory masterStepBuilderFactory;
    
    @Bean
    public DirectChannel stepThreeOutboundRequestChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow stepThreeOutboundRequestFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(stepThreeOutboundRequestChannel())
                .handle(Jms.outboundAdapter(connectionFactory).destination(REQUEST_CHANNEL))
                .get();
    }
    
    @Bean
    public DirectChannel stepThreeInboundResponseChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow stepThreeInboundResponseFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(connectionFactory).destination(RESPONSE_CHANNEL))
                .channel(stepThreeInboundResponseChannel())
                .get();
    }
    
    @Bean
    public Step stepThree() {
        return this.masterStepBuilderFactory.get("stepThree")
                .partitioner("stepThreeWorker", new StepThreePartitioner())
                .gridSize(GRID_SIZE)
                .outputChannel(stepThreeOutboundRequestChannel())
                .inputChannel(stepThreeInboundResponseChannel())
                .build();
    }
    
}
