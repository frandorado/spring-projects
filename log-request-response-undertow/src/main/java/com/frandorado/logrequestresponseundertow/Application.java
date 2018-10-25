package com.frandorado.logrequestresponseundertow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import io.undertow.server.handlers.RequestDumpingHandler;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public UndertowServletWebServerFactory undertowServletWebServerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addDeploymentInfoCustomizers(deploymentInfo ->
                deploymentInfo.addInitialHandlerChainWrapper(handler -> {
            return new RequestDumpingHandler(handler);
        }));
        
        return factory;
    }
    
}
    
    @Bean
    public UndertowEmbeddedServletContainerFactory undertowEmbeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory undertowEmbeddedServletContainerFactory = new UndertowEmbeddedServletContainerFactory();

        undertowEmbeddedServletContainerFactory.addDeploymentInfoCustomizers(deploymentInfo -> deploymentInfo.addInitialHandlerChainWrapper(handler -> {
            return new RequestDumpingHandler(handler);
        }));

        return undertowEmbeddedServletContainerFactory;
    }