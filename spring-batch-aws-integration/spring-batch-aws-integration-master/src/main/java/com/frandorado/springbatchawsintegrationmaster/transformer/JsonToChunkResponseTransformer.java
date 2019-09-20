package com.frandorado.springbatchawsintegrationmaster.transformer;

import java.io.IOException;
import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.integration.chunk.ChunkResponse;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonToChunkResponseTransformer extends JsonToObjectTransformer {
    
    @Override
    protected Object doTransform(Message<?> message) throws Exception {
        return buildChunkResponse(message);
    }
    
    private ChunkResponse buildChunkResponse(Message<?> message) throws IOException {
        Map map = new ObjectMapper().readValue(message.getPayload().toString(), Map.class);
        
        Integer jobId = (Integer) map.get("jobId");
        Integer sequence = (Integer) map.get("sequence");
        String messageContent = (String) map.get("message");
        Boolean status = (Boolean) map.get("successful");
        
        StepContribution stepContribution = new StepContribution(new StepExecution("-", null));
        
        return new ChunkResponse(status, sequence, Long.valueOf(jobId), stepContribution, messageContent);
    }
}
