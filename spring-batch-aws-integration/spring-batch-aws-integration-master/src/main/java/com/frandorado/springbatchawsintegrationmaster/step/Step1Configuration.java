package com.frandorado.springbatchawsintegrationmaster.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.integration.chunk.ChunkMessageChannelItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aws.inbound.SqsMessageDrivenChannelAdapter;
import org.springframework.integration.aws.outbound.SqsMessageHandler;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.frandorado.springbatchawsintegrationmaster.transformer.ChunkRequestToJsonTransformer;
import com.frandorado.springbatchawsintegrationmaster.transformer.JsonToChunkResponseTransformer;

@Configuration
public class Step1Configuration {
    
    private final String REQUEST_QUEUE_NAME = "step1-request.fifo";
    private final String RESPONSE_QUEUE_NAME = "step1-response.fifo";
    
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    Step1ItemReader step1ItemReader;
    
    @Autowired
    AmazonSQSAsync amazonSQSAsync;
    
    @Autowired
    ChunkRequestToJsonTransformer chunkRequestToJsonTransformer;
    
    @Autowired
    JsonToChunkResponseTransformer jsonToChunkResponseTransformer;
    
    // Step configuration
    @Bean
    @JobScope
    public Step step1() {
        Integer chunkSize = 10;
        return stepBuilderFactory.get("step1").chunk(chunkSize).reader(step1ItemReader).writer(chunkMessageChannelItemWriter(chunkSize))
                .build();
    }
    
    private ChunkMessageChannelItemWriter chunkMessageChannelItemWriter(Integer chunkSize) {
        ChunkMessageChannelItemWriter chunkMessageChannelItemWriter = new ChunkMessageChannelItemWriter();
        
        chunkMessageChannelItemWriter.setMessagingOperations(buildMessagingTemplate(step1RequestMesssageChannel()));
        chunkMessageChannelItemWriter.setReplyChannel(step1ResponseMessageChannel());
        chunkMessageChannelItemWriter.setThrottleLimit(chunkSize);
        
        return chunkMessageChannelItemWriter;
    }
    
    private MessagingTemplate buildMessagingTemplate(MessageChannel messageChannel) {
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        messagingTemplate.setDefaultChannel(messageChannel);
        return messagingTemplate;
    }
    
    private MessageHandler sqsMessageHandler() {
        SqsMessageHandler sqsMessageHandler = new SqsMessageHandler(amazonSQSAsync);
        sqsMessageHandler.setQueue(REQUEST_QUEUE_NAME);
        
        return sqsMessageHandler;
    }
    
    // Request configuration
    @Bean
    public MessageChannel step1RequestMesssageChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow step1RequestIntegrationFlow() {
        return IntegrationFlows.from(step1RequestMesssageChannel()).transform(chunkRequestToJsonTransformer).handle(sqsMessageHandler())
                .get();
    }
    
    // Response configuration
    @Bean
    public PollableChannel step1ResponseMessageChannel() {
        return new QueueChannel();
    }
    
    @Bean
    public IntegrationFlow step1ResponseIntegrationFlow() {
        return IntegrationFlows.from(sqsMessageDrivenChannelAdapter())
                .transform(jsonToChunkResponseTransformer).channel(step1ResponseMessageChannel()).get();
    }
    
    private SqsMessageDrivenChannelAdapter sqsMessageDrivenChannelAdapter() {
        return new SqsMessageDrivenChannelAdapter(amazonSQSAsync, RESPONSE_QUEUE_NAME);
    }
}
