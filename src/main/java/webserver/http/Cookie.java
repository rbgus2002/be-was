package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private final Map<String, String> directives;

    public Cookie() {
        this.directives = new HashMap<>();
    }

    public void add(String key, String value) {
        directives.put(key, value);
    }

    public String get(String key) {
        return directives.get(key);
    }   
}
