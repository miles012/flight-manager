package pl.miles.flightmanager.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonObjectMapper {

    private static final ObjectMapper INSTANCE = initJsonMapper();

    private JsonObjectMapper() {
    }

    private static ObjectMapper initJsonMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

    public static ObjectMapper jsonMapper() {
        return INSTANCE;
    }
}
