package com.frandorado.springbatchawsintegrationmaster.job;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainJobConfiguration {
    
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    Step step1;
    
    @Autowired
    MainJobExecutionListener mainJobExecutionListener;
    
    @Bean
    public Job job() {
        return jobBuilderFactory.get("job" + new Date()).listener(mainJobExecutionListener).start(step1).build();
    }
}
