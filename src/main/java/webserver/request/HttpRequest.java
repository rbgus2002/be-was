package webserver.request;

import java.util.Map;

public class HttpRequest {
    private final String method;
    private final HttpURL url;
    private final String version;
    private final String body;
    private final Map<String, String> headers;

    public HttpRequest(String method, HttpURL url, String version, Map<String, String> headers, String body) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.body = body;
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public HttpURL getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getBody() {
        return body;
    }
}
