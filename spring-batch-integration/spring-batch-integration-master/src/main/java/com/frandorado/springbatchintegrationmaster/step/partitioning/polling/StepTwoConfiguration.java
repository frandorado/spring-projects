package com.frandorado.springbatchintegrationmaster.step.partitioning.polling;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.jms.Jms;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;

@Configuration
public class StepTwoConfiguration {
    
    private static final String OUTBOUND_REQUEST_CHANNEL = "stepTwoRequestChannel";
    
    private static final int GRID_SIZE = 4;
    
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    public JobExplorer jobExplorer;
    
    @Bean
    public Step stepTwo() throws Exception {
        return stepBuilderFactory.get("stepTwo")
                .partitioner("stepTwoWorker", new StepTwoPartitioner())
                .partitionHandler(stepTwoPartitionHandler())
                .build();
    }
    
    @Bean
    public PartitionHandler stepTwoPartitionHandler() throws Exception {
        MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
        
        partitionHandler.setStepName("stepTwoWorker");
        partitionHandler.setGridSize(GRID_SIZE);
        partitionHandler.setMessagingOperations(stepTwoMessageTemplate());
        partitionHandler.setPollInterval(500000000l);
        partitionHandler.setJobExplorer(this.jobExplorer);
        
        partitionHandler.afterPropertiesSet();
        
        return partitionHandler;
    }
    
    @Bean
    public MessagingTemplate stepTwoMessageTemplate() {
        MessagingTemplate messagingTemplate = new MessagingTemplate(stepTwoOutboundRequests());
        messagingTemplate.setReceiveTimeout(60000000l);
        
        return messagingTemplate;
    }
    
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(10));
        return pollerMetadata;
    }
    
    @Bean(name = OUTBOUND_REQUEST_CHANNEL)
    public DirectChannel stepTwoOutboundRequests() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow stepTwoOutboundRequestsIntegrationFlow(ActiveMQConnectionFactory activeMQConnectionFactory) {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(activeMQConnectionFactory).destination(OUTBOUND_REQUEST_CHANNEL))
                .channel(stepTwoOutboundRequests())
                .get();
    }
    
}
