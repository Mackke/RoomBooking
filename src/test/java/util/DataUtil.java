package util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public final class DataUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    public static String convertObjectToString(Object domainObject) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(domainObject);
    }

    public static <T> T readValue(String data, Class<T> mapTarget) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(data, mapTarget);
    }
}
