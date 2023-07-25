package webserver.model;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private final Map<String, String> attributes = new HashMap<>();

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }
}
