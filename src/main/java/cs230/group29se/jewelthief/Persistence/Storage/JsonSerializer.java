package cs230.group29se.jewelthief.Persistence.Storage;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Provides JSON (string) <-> object conversion.
 * @author Iyaad
 * @version 1.0
 */
public class JsonSerializer {
    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    /** Converts any object to a JSON string. */
    public String toJson(Object o) {
        try { return mapper.writeValueAsString(o); }
        catch (Exception e) { throw new RuntimeException("toJson failed", e); }
    }

    /** Parses JSON into generic Java types (Map/List/Number/String/Boolean/null). */
    public Object fromJson(String json) {
        try { return mapper.readValue(json, Object.class); }
        catch (Exception e) { throw new RuntimeException("fromJson(Object) failed", e); }
    }

    // typed parse used internally where helpful
    public <T> T fromJson(String json, Class<T> type) {
        try { return mapper.readValue(json, type); }
        catch (Exception e) { throw new RuntimeException("fromJson typed failed", e); }
    }
}
