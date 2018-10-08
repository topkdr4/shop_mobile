package ru.vetoshkin.shop_mobile.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;





public class Json {
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static {
        jsonMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        jsonMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        jsonMapper.setDateFormat(dateFormat);
    }


    private Json() {

    }


    public static String toJson(Object object) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(object);
    }


    public static <T> T toObject(String json, Class<T> clazz) throws IOException {
        return jsonMapper.readValue(json, clazz);
    }


    public static ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

}
