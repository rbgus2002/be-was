package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequestHeader {

    private final Map<String, String> headers;

    public HttpRequestHeader(BufferedReader bufferedReader) throws IOException {
        // 불변 map
        this.headers = parseHeader(bufferedReader);
    }

    public String get(String key) {
        return this.headers.get(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        return this.headers.getOrDefault(key, defaultValue);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return this.headers.entrySet();
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
