package com.dastrix.api.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
public class JsonConfigure<T> {
    private static final ObjectMapper configure = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final Class<T> type;
    public JsonConfigure(Class<T> type) {
        this.type = type;
    }
    @SneakyThrows
    public T get(String response) {
        return configure.readValue(response, type);
    }
}
