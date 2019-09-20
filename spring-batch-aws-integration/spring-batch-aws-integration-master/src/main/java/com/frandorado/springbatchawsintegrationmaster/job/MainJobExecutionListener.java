package com.frandorado.springbatchawsintegrationmaster.job;

import java.util.Date;

import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MainJobExecutionListener implements JobExecutionListener {
    
    protected static final org.apache.commons.logging.Log logger = LogFactory.getLog(MainJobExecutionListener.class);
    
    @Override
    public void beforeJob(JobExecution jobExecution) {
        
    }
    
    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Job finished in " + diffTimeInSeconds(jobExecution.getStartTime(), jobExecution.getEndTime()) + " seconds");
    }
    
    private Long diffTimeInSeconds(Date startTime, Date endTime) {
        return startTime != null && endTime != null ? (endTime.getTime() - startTime.getTime()) / 1000 : null;
    }
}
