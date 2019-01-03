package com.frandorado.resilience4jspring;

import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.log4j.Log4j;

@SpringBootApplication
@Log4j
public class Resilience4jSpringApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(Resilience4jSpringApplication.class, args);
    }
    
    private static final Consumer<Runnable> consumer = runnable -> IntStream.range(0, 5).forEach(value -> {
        try {
            runnable.run();
        } catch (CircuitBreakerOpenException e) {
            log.warn("Circuit breaker applied");
        } catch (Exception e) {
            log.warn("Exception in method");
        }
    });
    
    @Bean
    CircuitBreaker defaultCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("default");
    }
    
    @Bean
    ApplicationRunner applicationRunner(CircuitBreakerTestService circuitBreakerTestService, CircuitBreaker defaultCircuitBreaker) {
        return applicationArguments -> {
            
            log.info("Running without circuit breaker ...");
            consumer.accept(() -> circuitBreakerTestService.timeout());
            
            log.info("Running with circuit breaker using annotations ...");
            consumer.accept(() -> circuitBreakerTestService.timeoutWithCircuitBreaker());
            
            log.info("Running with default circuit breaker using manual invocation ...");
            Runnable decoratedRunnable = CircuitBreaker.decorateRunnable(defaultCircuitBreaker, () -> circuitBreakerTestService.timeout());
            consumer.accept(() -> decoratedRunnable.run());
        };
    }
    
    @Service
    class CircuitBreakerTestService {
        
        public void timeout() {
            log.info("Entering in service ...");

            throw new HttpServerErrorException(HttpStatus.GATEWAY_TIMEOUT);
        }
        
        @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "serviceA")
        public void timeoutWithCircuitBreaker() {
            timeout();
        }
    }
    
}
