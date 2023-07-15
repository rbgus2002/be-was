package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaders {
    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public void addHeader(String headerName, String value) {
        headers.put(headerName, value);
    }

    public String getHeaderValues(String headerName) {
        return headers.getOrDefault(headerName, null);
    }

    public Set<String> getHeaderNames() {
        return headers.keySet();
    }
}
