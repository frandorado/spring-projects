package com.frandorado.springbatchawsintegrationmaster.step.step2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class Step2Partitioner extends SimplePartitioner {
    
    private static final String NUMBER_KEY = "number";
    
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> map = new HashMap(gridSize);
        
        for (int i = 0; i < gridSize; ++i) {
            ExecutionContext executionContext = new ExecutionContext();
            executionContext.put(NUMBER_KEY, new Random().nextInt(10000));
            
            String key = "partition" + i;
            map.put(key, executionContext);
        }
        
        return map;
    }
    
}
