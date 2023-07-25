package webserver.http.message;

import java.util.*;

public class HttpHeaders {
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String ACCEPT = "Accept";
    public static final String LOCATION = "Location";

    private final Map<String, List<String>> headers = new HashMap<>();

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void addHeader(String key, String... value) {
        List<String> values = headers.getOrDefault(key, new ArrayList<>());
        values.addAll(Arrays.asList(value));
        headers.put(key, values);
    }

    public int getContentLength() {
        if (!headers.containsKey(CONTENT_LENGTH)) {
            return -1;
        }
        String contentLength = getSingleValue(CONTENT_LENGTH);
        return Integer.parseInt(contentLength);
    }

    public List<String> get(String key) {
        return headers.getOrDefault(key, new ArrayList<>());
    }

    public String getSingleValue(String key) {
        return headers.get(key).get(0);
    }

    public boolean contains(String key) {
        return headers.containsKey(key);
    }

    @Override
    public String toString() {
        return "HttpHeaders{" + "headers=" + headers + '}';
    }
}

