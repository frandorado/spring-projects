package com.frandorado.springbatchawsintegrationmaster.step;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class Step1ItemReader extends ListItemReader<Integer> {
    
    public Step1ItemReader() {
        super(IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList()));
    }
}
