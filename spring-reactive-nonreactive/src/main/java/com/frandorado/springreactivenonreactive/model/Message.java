package com.frandorado.springreactivenonreactive.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "messages")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Message {
    
    @Id
    private String id;
    
    @NotBlank
    private String content;
    
    @NotNull
    private Date createdAt = new Date();
    
}