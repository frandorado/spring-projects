package com.frandorado.springdatamongowithcluster;


import org.springframework.data.annotation.Id;

import lombok.Builder;

@Builder
public class Car {
    
    @Id
    private String id;
    
    private String name;
}
