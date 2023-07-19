package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private final Map<String, String> headers;

    public HttpHeader(BufferedReader bufferedReader) throws IOException {
        // 불변 map
        this.headers = Collections.unmodifiableMap(parseHeader(bufferedReader));
    }

    public String get(String key) {
        return this.headers.get(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        return this.headers.getOrDefault(key, defaultValue);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    private Map<String, String> parseHeader(BufferedReader reader) throws IOException {
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(':');
            if (colonIndex != -1) {
                String headerName = line.substring(0, colonIndex).trim();
                String headerValue = line.substring(colonIndex + 1).trim();
                map.put(headerName, headerValue);
            }
        }
        return map;
    }
}
