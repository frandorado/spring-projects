package com.frandorado.springbatchawsintegrationslave.transformer;

import java.io.IOException;
import java.util.Map;

import org.springframework.batch.integration.partition.StepExecutionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.aws.support.AwsHeaders;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonToStepExecutionRequestTransformer extends JsonToObjectTransformer {
    
    @Autowired
    AmazonSQSAsync amazonSQSAsync;
    
    @Override
    protected Object doTransform(Message<?> message) throws Exception {
        // ACK
        ack(message);
        
        StepExecutionRequest stepExecutionRequest = buildStepExecutionRequest(message);
        return this.getMessageBuilderFactory().withPayload(stepExecutionRequest).build();
    }
    
    private StepExecutionRequest buildStepExecutionRequest(Message<?> message) throws IOException {
        Map map = new ObjectMapper().readValue(message.getPayload().toString(), Map.class);
        return new StepExecutionRequest((String) map.get("stepName"), Long.valueOf((Integer) map.get("jobExecutionId")),
                Long.valueOf((Integer) map.get("stepExecutionId")));
    }
    
    private void ack(Message<?> message) {
        String receiptHandle = message.getHeaders().get(AwsHeaders.RECEIPT_HANDLE, String.class);
        String queue = message.getHeaders().get(AwsHeaders.QUEUE, String.class);
        String queueUrl = amazonSQSAsync.getQueueUrl(queue).getQueueUrl();
        
        amazonSQSAsync.deleteMessage(queueUrl, receiptHandle);
    }
}
