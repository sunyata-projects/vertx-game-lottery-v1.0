package org.squirrelframework.foundation.fsm.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.IOException;

/**
 * Created by leo on 17/5/2.
 */
public class StateMachineJsonDeserializer extends JsonDeserializer<ListMultimap> {

    @Override
    public ListMultimap deserialize(JsonParser p, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {

        ArrayListMultimap<Object, Object> objectObjectArrayListMultimap = ArrayListMultimap.create();
        return objectObjectArrayListMultimap;
    }
}
