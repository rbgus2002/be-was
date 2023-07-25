package webserver.http;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

public class HttpHeaders {
    private final Map<String, String> headers;

    public HttpHeaders() {
        this.headers = Maps.newHashMap();
    }

    public void setHeader(String header, String value) {
        this.headers.put(header, value);
    }

    public String getHeader(String header) {
        return this.headers.getOrDefault(header, null);
    }

    public Set<String> headers() {
        return this.headers.keySet();
    }

}
