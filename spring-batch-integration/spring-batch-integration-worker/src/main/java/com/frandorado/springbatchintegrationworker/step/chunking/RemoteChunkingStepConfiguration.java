package com.frandorado.springbatchintegrationworker.step.chunking;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

@Configuration
public class RemoteChunkingStepConfiguration {
    
    @Autowired
    ActiveMQConnectionFactory activeMQConnectionFactory;
    
    
    protected DirectChannel buildInputDirectChannel() {
        return new DirectChannel();
    }
    
    protected DirectChannel buildOutputDirectChannel() {
        return new DirectChannel();
    }
    
    public IntegrationFlow buildInputIntegrationFlow(String destination, DirectChannel channel) {
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(activeMQConnectionFactory).destination(destination))
                .channel(channel)
                .get();
    }
    
    public IntegrationFlow buildOutputIntegrationFlow(String destination, DirectChannel channel) {
        return IntegrationFlows
                .from(channel)
                .handle(Jms.outboundAdapter(activeMQConnectionFactory).destination(destination))
                .get();
    }
    
    
}
