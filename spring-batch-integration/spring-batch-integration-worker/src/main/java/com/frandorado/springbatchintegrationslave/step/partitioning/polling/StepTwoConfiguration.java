package com.frandorado.springbatchintegrationslave.step.partitioning.polling;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.integration.partition.BeanFactoryStepLocator;
import org.springframework.batch.integration.partition.StepExecutionRequestHandler;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.NullChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
public class StepTwoConfiguration {
    
    private static final String INBOUND_CHANNEL = "stepTwoRequestChannel";
    private static final String OUTBOUND_CHANNEL = "stepTwoResponseChannel";
    
    
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    public JobExplorer jobExplorer;
    
    @Autowired
    public ApplicationContext applicationContext;
    
    @Bean
    public Step stepTwoWorker() {
        return stepBuilderFactory.get("stepTwoWorker")
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
    
    @Bean
    public StepExecutionRequestHandler stepExecutionRequestHandler() {
        StepExecutionRequestHandler stepExecutionRequestHandler = new StepExecutionRequestHandler();
        stepExecutionRequestHandler.setJobExplorer(jobExplorer);
        
        BeanFactoryStepLocator stepLocator = new BeanFactoryStepLocator();
        stepLocator.setBeanFactory(this.applicationContext);
        
        stepExecutionRequestHandler.setStepLocator(stepLocator);
        return stepExecutionRequestHandler;
    }
    
    @Bean
    @ServiceActivator(inputChannel = INBOUND_CHANNEL, outputChannel = OUTBOUND_CHANNEL)
    public StepExecutionRequestHandler stepTwoServiceActivator() {
        return stepExecutionRequestHandler();
    }
    
    @Bean(name = OUTBOUND_CHANNEL)
    public PollableChannel stepTwoResponseChannel() {
        return new NullChannel();
    }
}
