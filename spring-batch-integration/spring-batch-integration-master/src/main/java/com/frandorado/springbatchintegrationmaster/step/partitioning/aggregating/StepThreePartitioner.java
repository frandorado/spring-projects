package com.frandorado.springbatchintegrationmaster.step.partitioning.aggregating;

import java.util.Map;

import org.springframework.batch.core.partition.support.SimplePartitioner;
import org.springframework.batch.item.ExecutionContext;

public class StepThreePartitioner extends SimplePartitioner {
    
    private static final String PARTITION_KEY = "partition";
    
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitions = super.partition(gridSize);
        int i = 0;
        for (ExecutionContext context : partitions.values()) {
            context.put(PARTITION_KEY, PARTITION_KEY + (i++) + "-threepartitioner");
        }
        return partitions;
    }
    
}
