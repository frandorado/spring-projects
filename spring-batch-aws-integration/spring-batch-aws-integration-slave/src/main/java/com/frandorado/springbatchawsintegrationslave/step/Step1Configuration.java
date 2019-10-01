package com.frandorado.springbatchawsintegrationslave.step;

import org.springframework.batch.core.step.item.SimpleChunkProcessor;
import org.springframework.batch.integration.chunk.ChunkProcessorChunkHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aws.inbound.SqsMessageDrivenChannelAdapter;
import org.springframework.integration.aws.outbound.SqsMessageHandler;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.frandorado.springbatchawsintegrationslave.transformer.ChunkResponseToJsonTransformer;
import com.frandorado.springbatchawsintegrationslave.transformer.JsonToChunkRequestTransformer;

@Configuration
public class Step1Configuration {
    
    private final String REQUEST_QUEUE_NAME = "step1-request.fifo";
    private final String RESPONSE_QUEUE_NAME = "step1-response.fifo";
    
    @Autowired
    Step1ItemWriter step1ItemWriter;
    
    @Autowired
    AmazonSQSAsync amazonSQSAsync;
    
    @Autowired
    JsonToChunkRequestTransformer jsonToChunkRequestTransformer;
    
    @Autowired
    ChunkResponseToJsonTransformer chunkResponseToJsonTransformer;
    
    // Processor
    @Bean
    public ChunkProcessorChunkHandler step1ChunkProcessorChunkHandler() {
        ChunkProcessorChunkHandler slaveChunkProcessorChunkHandler = new ChunkProcessorChunkHandler();
        slaveChunkProcessorChunkHandler.setChunkProcessor(new SimpleChunkProcessor(null, step1ItemWriter));
        return slaveChunkProcessorChunkHandler;
    }
    
    // Request
    @Bean
    public IntegrationFlow step1RequestIntegrationFlow() {
        return IntegrationFlows.from(buildRequestSqsMessageDrivenChannelAdapter()).transform(jsonToChunkRequestTransformer)
                .handle(step1ChunkProcessorChunkHandler()).channel(step1ResponseMessageChannel()).get();
    }
    
    @Bean
    public MessageChannel step1RequestMessageChannel() {
        return new DirectChannel();
    }
    
    private SqsMessageDrivenChannelAdapter buildRequestSqsMessageDrivenChannelAdapter() {
        SqsMessageDrivenChannelAdapter adapter = new SqsMessageDrivenChannelAdapter(amazonSQSAsync, REQUEST_QUEUE_NAME);
        
        adapter.setOutputChannel(step1RequestMessageChannel());
        // ACK in transformer
        adapter.setMessageDeletionPolicy(SqsMessageDeletionPolicy.NEVER);
        adapter.setMaxNumberOfMessages(1);
        return adapter;
    }
    
    // Response
    @Bean
    public IntegrationFlow step1ResponseIntegrationFlow() {
        return IntegrationFlows.from(step1ResponseMessageChannel()).transform(chunkResponseToJsonTransformer).handle(sqsMessageHandler())
                .get();
    }
    
    @Bean
    public MessageChannel step1ResponseMessageChannel() {
        return new DirectChannel();
    }
    
    private SqsMessageHandler sqsMessageHandler() {
        SqsMessageHandler sqsMessageHandler = new SqsMessageHandler(amazonSQSAsync);
        sqsMessageHandler.setQueue(RESPONSE_QUEUE_NAME);
        
        return sqsMessageHandler;
    }
    
}
