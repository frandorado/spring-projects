package com.frandorado.springcustomserializerdeserializer.deserialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.frandorado.springcustomserializerdeserializer.controller.model.PersonRequest;

public class PersonRequestCustomDeserializer {
    
    public PersonRequest deserialize(JsonParser jsonParser) throws IOException {
        
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        String fullName = jsonNode.get("full_name").textValue();
        
        return PersonRequest.builder().fullName(fullName).build();
    }
}
