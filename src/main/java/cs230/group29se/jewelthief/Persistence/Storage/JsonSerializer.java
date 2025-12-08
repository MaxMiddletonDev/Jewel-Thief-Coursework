package cs230.group29se.jewelthief.Persistence.Storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Provides functionality for converting objects to JSON strings and vice versa.
 * Utilizes the Jackson library for serialization and deserialization.
 * <p>
 * Author: Iyaad
 * @version 1.0
 */
public class JsonSerializer {
    // ObjectMapper instance configured to produce pretty-printed JSON output
    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private final static String TO_JSON_FAIL_MSG = "toJson failed";
    private final static String FROM_JSON_FAIL_MSG = "fromJson(Object) failed";
    private final static String FROM_JSON_TYPED_FAIL_MSG = "fromJson typed failed";

    /**
     * Converts an object to its JSON string representation.
     *
     * @param o The object to be converted to JSON.
     * @return The JSON string representation of the object.
     * @throws RuntimeException if the conversion fails.
     */
    public String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(TO_JSON_FAIL_MSG, e);
        }
    }

    /**
     * Parses a JSON string into a generic Java object.
     * The resulting object can be a Map, List, Number, String, Boolean, or null.
     *
     * @param json The JSON string to be parsed.
     * @return The parsed Java object.
     * @throws RuntimeException if the parsing fails.
     */
    public Object fromJson(String json) {
        try {
            return mapper.readValue(json, Object.class);
        } catch (Exception e) {
            throw new RuntimeException(FROM_JSON_FAIL_MSG, e);
        }
    }

    /**
     * Parses a JSON string into a specific Java type.
     *
     * @param <T>  The type of the object to be returned.
     * @param json The JSON string to be parsed.
     * @param type The class of the type to parse the JSON into.
     * @return The parsed object of the specified type.
     * @throws RuntimeException if the parsing fails.
     */
    public <T> T fromJson(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(FROM_JSON_TYPED_FAIL_MSG, e);
        }
    }
}
