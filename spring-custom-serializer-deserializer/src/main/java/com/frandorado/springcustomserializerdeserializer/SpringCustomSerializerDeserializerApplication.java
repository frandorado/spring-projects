package com.frandorado.springcustomserializerdeserializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringCustomSerializerDeserializerApplication {
    
    public static final String CUSTOM_API = "custom-api";
    
    public static void main(String[] args) {
        SpringApplication.run(SpringCustomSerializerDeserializerApplication.class, args);
    }
}
