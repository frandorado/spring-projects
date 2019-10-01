package com.frandorado.springbatchawsintegrationslave.transformer;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.integration.chunk.ChunkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.aws.support.AwsHeaders;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonToChunkRequestTransformer extends JsonToObjectTransformer {
    
    private static final String MESSAGE_GROUP_ID_HEADER = "message-group-id";
    
    @Autowired
    AmazonSQSAsync amazonSQSAsync;
    
    @Override
    protected Object doTransform(Message<?> message) throws Exception {
        // ACK
        ack(message);
        
        return this.getMessageBuilderFactory().withPayload(buildChunkRequest(message)).setHeader(MESSAGE_GROUP_ID_HEADER, "unique").build();
    }
    
    private ChunkRequest buildChunkRequest(Message<?> message) throws IOException {
        Map map = new ObjectMapper().readValue(message.getPayload().toString(), Map.class);
        Map stepContributionMap = (Map) map.get("stepContribution");
        Map exitStatusMap = (Map) stepContributionMap.get("exitStatus");
        
        StepContribution stepContribution = new StepContribution(new StepExecution("null", null));
        ExitStatus exitStatus = new ExitStatus((String) exitStatusMap.get("exitCode"), (String) exitStatusMap.get("exitDescription"));
        
        IntStream.range(0, (Integer) stepContributionMap.get("readCount")).forEach(e -> stepContribution.incrementReadCount());
        stepContribution.incrementWriteCount((Integer) stepContributionMap.get("writeCount"));
        stepContribution.incrementFilterCount((Integer) stepContributionMap.get("filterCount"));
        stepContribution.incrementReadSkipCount((Integer) stepContributionMap.get("readSkipCount"));
        IntStream.range(0, (Integer) stepContributionMap.get("writeSkipCount")).forEach(e -> stepContribution.incrementWriteSkipCount());
        IntStream.range(0, (Integer) stepContributionMap.get("processSkipCount"))
                .forEach(e -> stepContribution.incrementProcessSkipCount());
        stepContribution.setExitStatus(exitStatus);
        
        return new ChunkRequest((Integer) map.get("sequence"), (Collection) map.get("items"), (Integer) map.get("jobId"), stepContribution);
    }
    
    private void ack(Message<?> message) {
        String receiptHandle = message.getHeaders().get(AwsHeaders.RECEIPT_HANDLE, String.class);
        String queue = message.getHeaders().get(AwsHeaders.QUEUE, String.class);
        String queueUrl = amazonSQSAsync.getQueueUrl(queue).getQueueUrl();
        
        amazonSQSAsync.deleteMessage(queueUrl, receiptHandle);
    }
}
