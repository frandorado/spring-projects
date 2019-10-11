package com.frandorado.springbatchawsintegrationmaster.step.step2;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.integration.partition.MessageChannelPartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aws.outbound.SqsMessageHandler;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.frandorado.springbatchawsintegrationmaster.transformer.StepExecutionRequestToJsonTransformer;

@Configuration
public class Step2Configuration {
    
    private final String REQUEST_QUEUE_NAME = "step2-request.fifo";
    private final String STEP_NAME = "step2";
    
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    AmazonSQSAsync amazonSQSAsync;
    
    @Autowired
    Step2Partitioner step2Partitioner;
    
    @Autowired
    JobExplorer jobExplorer;
    
    @Autowired
    StepExecutionRequestToJsonTransformer stepExecutionRequestToJsonTransformer;
    
    @Bean
    public Step step2() {
        return stepBuilderFactory.get(STEP_NAME).partitioner(STEP_NAME, step2Partitioner).partitionHandler(buildPartitionHandler()).build();
    }
    
    @Bean
    public PartitionHandler buildPartitionHandler() {
        MessageChannelPartitionHandler partitionHandler = new MessageChannelPartitionHandler();
        
        partitionHandler.setGridSize(10);
        partitionHandler.setStepName(STEP_NAME);
        partitionHandler.setMessagingOperations(buildMessagingTemplate(step2RequestMessageChannel()));
        partitionHandler.setJobExplorer(jobExplorer);
        partitionHandler.setTimeout(30 * 60 * 1000); // 30 minutes
        partitionHandler.setPollInterval(10000); // 10 seconds
        
        return partitionHandler;
    }
    
    // Remote configuration
    @Bean
    public MessageChannel step2RequestMessageChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow step2RequestIntegrationFlow() {
        return IntegrationFlows.from(step2RequestMessageChannel()).transform(stepExecutionRequestToJsonTransformer)
                .handle(buildMessageHandler()).get();
    }
    
    private MessagingTemplate buildMessagingTemplate(MessageChannel messageChannel) {
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        messagingTemplate.setDefaultChannel(messageChannel);
        return messagingTemplate;
    }
    
    private MessageHandler buildMessageHandler() {
        SqsMessageHandler sqsMessageHandler = new SqsMessageHandler(amazonSQSAsync);
        sqsMessageHandler.setQueue(REQUEST_QUEUE_NAME);
        
        return sqsMessageHandler;
    }
    
}
