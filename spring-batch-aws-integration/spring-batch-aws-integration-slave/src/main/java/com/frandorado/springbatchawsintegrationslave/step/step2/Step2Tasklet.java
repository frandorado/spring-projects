package com.frandorado.springbatchawsintegrationslave.step.step2;

import java.math.BigInteger;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.frandorado.springbatchawsintegrationslave.step.step1.Step1ItemWriter;

@Component
@StepScope
public class Step2Tasklet implements Tasklet {
    
    private static final Log log = LogFactory.getLog(Step1ItemWriter.class);
    
    private static final String NUMBER_KEY = "number";
    
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Map<String, Object> stepExecutionContext = chunkContext.getStepContext().getStepExecutionContext();
        BigInteger number = BigInteger.valueOf((Integer) stepExecutionContext.get(NUMBER_KEY));
        Integer nextProbablePrime = number.nextProbablePrime().intValue();
        
        log.info("The number received is " + number + " and the next probable prime is " + nextProbablePrime);
        
        return RepeatStatus.FINISHED;
    }
}
