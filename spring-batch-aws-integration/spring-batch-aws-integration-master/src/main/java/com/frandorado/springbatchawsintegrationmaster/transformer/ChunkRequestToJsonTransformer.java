package com.frandorado.springbatchawsintegrationmaster.transformer;

import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.mapping.support.JsonHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ChunkRequestToJsonTransformer extends ObjectToJsonTransformer {
    
    private static final String CLASS_NAME_HEADER = "className";
    private static final String MESSAGE_GROUP_ID_HEADER = "message-group-id";
    
    @Override
    protected Object doTransform(Message<?> message) throws Exception {
        Message jsonMessage = (Message) super.doTransform(message);
//        String className = ((Class) jsonMessage.getHeaders().get(JsonHeaders.TYPE_ID)).getName();
//
//        return this.getMessageBuilderFactory().withPayload(jsonMessage.getPayload()).copyHeaders(message.getHeaders())
//                .removeHeaders((String[]) JsonHeaders.HEADERS.toArray(new String[3])).setHeader(CLASS_NAME_HEADER, className)
//                .setHeader(MESSAGE_GROUP_ID_HEADER, "uniqueGroupHeader").build();
        return this.getMessageBuilderFactory().withPayload(jsonMessage.getPayload()).setHeader(MESSAGE_GROUP_ID_HEADER,
                "uniqueGroupHeader").build();
    }
}
