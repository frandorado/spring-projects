package com.frandorado.springbatchintegrationworker.step.partitioning.polling;

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
public class StepTwoWorkerConfiguration {
    
    private static final String REQUEST_CHANNEL = "stepTwoRequestChannel";
    
    @Autowired
    private RemotePartitioningWorkerStepBuilderFactory workerStepBuilderFactory;
    
    
    @Bean
    public DirectChannel inboundRequestChannel() {
        return new DirectChannel();
    }
    
    @Bean
    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(connectionFactory).destination(REQUEST_CHANNEL))
                .channel(inboundRequestChannel())
                .get();
    }
    
    @Bean
    public Step stepTwoWorker() {
        return this.workerStepBuilderFactory.get("stepTwoWorker")
                .inputChannel(inboundRequestChannel())
                .tasklet(tasklet(null))
                .build();
    }
    
    @Bean
    @StepScope
    public Tasklet tasklet(@Value("#{stepExecutionContext['partition']}") String partition) {
        return (contribution, chunkContext) -> {
            System.out.println("processing " + partition);
            return RepeatStatus.FINISHED;
        };
    }
    
    
}
