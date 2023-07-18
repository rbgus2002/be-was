package webserver.request;

import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String url;
    private final String version;
    private final String body;
    private final Map<String, String> headers;

    public HttpRequest(String method, String url, String version, Map<String, String> headers, String body) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.body = body;
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getMetaData(String key) {
        return headers.get(key);
    }

    public String getBody() {
        return body;
    }
}
