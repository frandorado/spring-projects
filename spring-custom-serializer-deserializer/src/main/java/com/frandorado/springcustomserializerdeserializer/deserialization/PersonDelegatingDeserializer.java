package com.frandorado.springcustomserializerdeserializer.deserialization;

import static com.frandorado.springcustomserializerdeserializer.SpringCustomSerializerDeserializerApplication.CUSTOM_API;

import java.io.IOException;

import org.slf4j.MDC;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;

public class PersonDelegatingDeserializer extends DelegatingDeserializer {
    
    private final PersonRequestCustomDeserializer personRequestCustomDeserializer = new PersonRequestCustomDeserializer();
    
    public PersonDelegatingDeserializer(JsonDeserializer defaultJsonDeserializer) {
        super(defaultJsonDeserializer);
    }
    
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (MDC.get(CUSTOM_API) == null) {
            return super.deserialize(jsonParser, deserializationContext);
        } else {
            return personRequestCustomDeserializer.deserialize(jsonParser);
        }
    }
    
    @Override
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> jsonDeserializer) {
        return jsonDeserializer;
    }
}
