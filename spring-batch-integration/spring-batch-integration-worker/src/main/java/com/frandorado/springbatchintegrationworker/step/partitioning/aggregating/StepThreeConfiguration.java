package com.frandorado.springbatchintegrationworker.step.partitioning.aggregating;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.integration.partition.RemotePartitioningWorkerStepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

@Configuration
public class StepThreeConfiguration {
    
    private static final String REQUEST_CHANNEL = "stepThreeRequestChannel";
    private static final String RESPONSE_CHANNEL = "stepThreeResponseChannel";
    
    @Autowired
    RemotePartitioningWorkerStepBuilderFactory workerStepBuilderFactory;
    
    @Bean
    public DirectChannel stepThreeRequestInboundChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow stepThreeRequestInboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(connectionFactory).destination(REQUEST_CHANNEL))
                .channel(stepThreeRequestInboundChannel())
                .get();
    }
    
    /*
     * Configure outbound flow (replies going to the master)
     */
    @Bean
    public DirectChannel stepThreeResponseOutboundChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow stepThreeResponseOutboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(stepThreeResponseOutboundChannel())
                .handle(Jms.outboundAdapter(connectionFactory).destination(RESPONSE_CHANNEL))
                .get();
    }
    
    /*
     * Configure the worker step
     */
    @Bean
    public Step stepThreeWorker() {
        return this.workerStepBuilderFactory.get("stepThreeWorker")
                .inputChannel(stepThreeRequestInboundChannel())
                .outputChannel(stepThreeResponseOutboundChannel())
                .tasklet(stepThreeTasklet(null))
                .build();
    }
    
    @Bean
    @StepScope
    public Tasklet stepThreeTasklet(@Value("#{stepExecutionContext['partition']}") String partition) {
        return (contribution, chunkContext) -> {
            System.out.println("processing " + partition);
            return RepeatStatus.FINISHED;
        };
    }
}
