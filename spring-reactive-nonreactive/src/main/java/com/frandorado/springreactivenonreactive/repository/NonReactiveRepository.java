package com.frandorado.springreactivenonreactive.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.frandorado.springreactivenonreactive.model.Message;

@Repository
public interface NonReactiveRepository extends MongoRepository<Message, String> {

}
