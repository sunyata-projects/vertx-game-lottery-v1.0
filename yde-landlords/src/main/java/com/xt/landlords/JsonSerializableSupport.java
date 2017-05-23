package com.xt.landlords;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by leo on 17/4/28.
 */
public class JsonSerializableSupport {

    public static String serialize(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

            String stringData = mapper.writeValueAsString(data);
            return stringData;
        } catch (Exception ex) {
            throw new RuntimeException("Data serialization failed.", ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String value, Class<T> tClass) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            T t = mapper.readValue(value, tClass);
            return t;
        } catch (Exception e) {
            throw new Exception("Data serialization failed.", e);
        }
    }
}
