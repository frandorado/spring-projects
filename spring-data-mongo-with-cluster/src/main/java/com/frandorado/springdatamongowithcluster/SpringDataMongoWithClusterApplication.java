package com.frandorado.springdatamongowithcluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import lombok.extern.log4j.Log4j2;


@SpringBootApplication
@Log4j2
public class SpringDataMongoWithClusterApplication implements CommandLineRunner {
    
    @Autowired
    MongoTemplate mongoTemplate;
    
    public static void main(String[] args) {
        SpringApplication.run(SpringDataMongoWithClusterApplication.class, args);
    }
    
    @Override
    public void run(String... args) {
        
        mongoTemplate.dropCollection(Car.class);
        
        mongoTemplate.save(Car.builder().name("Tesla Model S").build());
        mongoTemplate.save(Car.builder().name("Tesla Model 3").build());
        
        log.info("-------------------------------");
        log.info("Cards found: " + mongoTemplate.count(new Query(), Car.class));
        log.info("-------------------------------");
    }
}
