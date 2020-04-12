package com.frandorado.springmicrometerundertow;

import io.micrometer.core.instrument.MeterRegistry;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.handlers.MetricsHandler;
import io.undertow.servlet.api.MetricsCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringMicrometerUndertowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMicrometerUndertowApplication.class, args);
    }

    @Bean
    UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer(UndertowMetricsHandlerWrapper undertowMetricsHandlerWrapper) {

        return deploymentInfo -> deploymentInfo.addOuterHandlerChainWrapper(undertowMetricsHandlerWrapper);
        //return deploymentInfo -> deploymentInfo.addOuterHandlerChainWrapper(MetricsHandler.WRAPPER);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> micrometerMeterRegistryCustomizer(@Value("${spring.application.name}") String applicationName) {
        return registry -> registry.config().commonTags("application.name", applicationName);
    }

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(@RequestParam(name = "name", required = true) String name) {
        return "Hello " + name + "!";
    }

}
