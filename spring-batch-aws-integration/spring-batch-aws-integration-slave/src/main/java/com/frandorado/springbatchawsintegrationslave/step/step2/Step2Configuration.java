package com.frandorado.springbatchawsintegrationslave.step.step2;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.integration.partition.BeanFactoryStepLocator;
import org.springframework.batch.integration.partition.StepExecutionRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aws.inbound.SqsMessageDrivenChannelAdapter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.frandorado.springbatchawsintegrationslave.transformer.JsonToStepExecutionRequestTransformer;

@Configuration
public class Step2Configuration {
    
    private final String REQUEST_QUEUE_NAME = "step2-request.fifo";
    private final String STEP_NAME = "step2";
    
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    AmazonSQSAsync amazonSQSAsync;
    
    @Autowired
    BeanFactoryStepLocator beanFactoryStepLocator;
    
    @Autowired
    JobExplorer jobExplorer;
    
    @Autowired
    JsonToStepExecutionRequestTransformer jsonToStepExecutionRequestTransformer;
    
    @Autowired
    Step2Tasklet step2Tasklet;
    
    @Bean
    public Step step2() {
        return stepBuilderFactory.get(STEP_NAME).tasklet(step2Tasklet).build();
    }
    
    // Remote request configuration
    @Bean
    public MessageChannel step2RequestMessageChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow step2RequestIntegrationFlow() {
        return IntegrationFlows.from(buildSqsMessageDrivenChannelAdapter()).transform(jsonToStepExecutionRequestTransformer)
                .handle(buildStepExecutionRequestHandler()).channel("nullChannel").get();
    }
    
    private SqsMessageDrivenChannelAdapter buildSqsMessageDrivenChannelAdapter() {
        SqsMessageDrivenChannelAdapter adapter = new SqsMessageDrivenChannelAdapter(amazonSQSAsync, REQUEST_QUEUE_NAME);
        adapter.setOutputChannel(step2RequestMessageChannel());
        adapter.setMessageDeletionPolicy(SqsMessageDeletionPolicy.NEVER);
        adapter.setMaxNumberOfMessages(1);
        return adapter;
    }
    
    private StepExecutionRequestHandler buildStepExecutionRequestHandler() {
        StepExecutionRequestHandler stepExecutionRequestHandler = new StepExecutionRequestHandler();
        stepExecutionRequestHandler.setStepLocator(beanFactoryStepLocator);
        stepExecutionRequestHandler.setJobExplorer(jobExplorer);
        return stepExecutionRequestHandler;
    }
}
