package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpParameters {
    private final Map<String, String> parameters;

    public HttpParameters() {
        parameters = new HashMap<>();
    }

    public String get(String name) {
        if(parameters.get(name) == null) {
            return null;
        }
        return new String(parameters.get(name));
    }

    public void put(String name, String value) {
        parameters.put(name, value);
    }

    public int size() {
        return parameters.size();
    }
}
