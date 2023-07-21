package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public String get(String field) {
        return new String(headers.get(field));
    }

    public void put(String field, String value) {
        headers.put(field, value);
    }

}
