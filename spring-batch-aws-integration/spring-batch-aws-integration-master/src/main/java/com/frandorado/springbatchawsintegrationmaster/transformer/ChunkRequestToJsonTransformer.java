package com.frandorado.springbatchawsintegrationmaster.transformer;

import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ChunkRequestToJsonTransformer extends ObjectToJsonTransformer {
    
    private static final String MESSAGE_GROUP_ID_HEADER = "message-group-id";
    
    @Override
    protected Object doTransform(Message<?> message) throws Exception {
        Message jsonMessage = (Message) super.doTransform(message);
        
        return this.getMessageBuilderFactory().withPayload(jsonMessage.getPayload()).setHeader(MESSAGE_GROUP_ID_HEADER, "unique").build();
    }
}
