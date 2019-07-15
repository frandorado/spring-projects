package com.frandorado.springbatchintegrationmaster.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.frandorado.springbatchintegrationmaster.conf.BrokerConfiguration;
import com.frandorado.springbatchintegrationmaster.conf.DatasourceConfiguration;

@Configuration
@Import(value = {DatasourceConfiguration.class, BrokerConfiguration.class})
public class JobConfiguration {
    
    @Autowired
    Step stepOne;
    
    @Autowired
    Step stepTwo;
    
    @Bean
    public Job mainJob(JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("mainJob")
                //.start(stepOne)
                .start(stepTwo)
                .build();
    }
    
}
