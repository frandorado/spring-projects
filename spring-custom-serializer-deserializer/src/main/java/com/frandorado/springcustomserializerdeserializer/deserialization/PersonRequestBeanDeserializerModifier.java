package com.frandorado.springcustomserializerdeserializer.deserialization;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.frandorado.springcustomserializerdeserializer.controller.model.PersonRequest;

public class PersonRequestBeanDeserializerModifier extends BeanDeserializerModifier {
    
    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc,
            JsonDeserializer<?> deserializer) {
        if (PersonRequest.class.equals(beanDesc.getBeanClass())) {
            return new PersonDelegatingDeserializer(deserializer);
        }
        return super.modifyDeserializer(config, beanDesc, deserializer);
    }
}
