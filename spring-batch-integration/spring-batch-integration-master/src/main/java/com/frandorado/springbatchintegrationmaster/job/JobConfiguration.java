package com.frandorado.springbatchintegrationmaster.job;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;

import com.frandorado.springbatchintegrationmaster.conf.BrokerConfiguration;
import com.frandorado.springbatchintegrationmaster.conf.DatasourceConfiguration;

@Configuration
@Import(value = {DatasourceConfiguration.class, BrokerConfiguration.class})
public class JobConfiguration {
    
    @Autowired
    Step stepOne;
    
    @Autowired
    JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    JobLauncher jobLauncher;
    
    @Autowired
    Step stepTwo;
    
    @Bean
    public Job mainJob() {
        return jobBuilderFactory.get("mainJob")
                //.start(stepOne)
                //.next(stepTwo)
                .start(stepTwo)
                .build();
    }
    
    @Scheduled(fixedDelay = 10000)
    public void schedule() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(mainJob(), new JobParametersBuilder()
                .addDate("date", new Date())
                .toJobParameters());
    }
    
}
