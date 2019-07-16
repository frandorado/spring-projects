package com.frandorado.springbatchintegrationmaster.step.partitioning.polling;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.integration.partition.RemotePartitioningMasterStepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

@Configuration
public class StepTwoConfiguration {
    
    private static final String REQUEST_CHANNEL = "stepTwoRequestChannel";
    
    private static final int GRID_SIZE = 3;
    
    @Autowired
    RemotePartitioningMasterStepBuilderFactory masterStepBuilderFactory;
    
    
    @Bean
    public DirectChannel stepTwoRequestOutbound() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow stepTwoOutboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(stepTwoRequestOutbound())
                .handle(Jms.outboundAdapter(connectionFactory).destination(REQUEST_CHANNEL))
                .get();
    }
    
    @Bean
    public Step stepTwo() {
        return this.masterStepBuilderFactory.get("stepTwo")
                .partitioner("stepTwoWorker", new StepTwoPartitioner())
                .gridSize(GRID_SIZE)
                .outputChannel(stepTwoRequestOutbound())
                .build();
    }
    
    
}
