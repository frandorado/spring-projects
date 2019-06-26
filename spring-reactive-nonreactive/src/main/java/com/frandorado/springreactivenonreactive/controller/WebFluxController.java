package com.frandorado.springreactivenonreactive.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frandorado.springreactivenonreactive.model.Message;
import com.frandorado.springreactivenonreactive.repository.ReactiveRepository;

import reactor.core.publisher.Mono;

@RestController
public class WebFluxController {
    
    @Autowired
    ReactiveRepository reactiveRepository;
    
    @RequestMapping("/webflux/{id}")
    public Mono<Message> findByIdReactive(@PathVariable(value = "id") String id) {
        return reactiveRepository.findById(id);
    }
    
    @PostMapping("/webflux")
    public Mono<Message> postReactive(@Valid @RequestBody Message message) {
        return reactiveRepository.save(message);
    }
    
    @DeleteMapping("/webflux")
    public Mono<Void> deleteAllReactive() {
        return reactiveRepository.deleteAll();
    }
}

