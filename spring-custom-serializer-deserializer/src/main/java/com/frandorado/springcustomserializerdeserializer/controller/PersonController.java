package com.frandorado.springcustomserializerdeserializer.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.frandorado.springcustomserializerdeserializer.controller.model.PersonRequest;

@RestController
public class PersonController {
    
    AtomicLong counter = new AtomicLong(0);
    
    @PostMapping(value = "/person", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> registerPerson(@RequestBody @Validated PersonRequest personRequest) {
        return ResponseEntity.ok("OK");
    }
    
}
