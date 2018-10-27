package com.frandorado.loggingrequestresponsewithbody.controller;

import com.frandorado.loggingrequestresponsewithbody.model.GreetingRequest;
import com.frandorado.loggingrequestresponsewithbody.model.GreetingResponse;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final AtomicLong count = new AtomicLong();

    @GetMapping("greetings/{id}")
    public GreetingResponse getGreeting(@PathVariable("id") Long id) {
        return GreetingResponse.builder().id(id).message("Hello world!").build();
    }

    @PostMapping("greetings")
    public GreetingResponse createGreeting(@RequestBody GreetingRequest greetingRequest) {
        return GreetingResponse.builder().id(count.incrementAndGet()).message(greetingRequest.getMessage()).build();
    }
}
