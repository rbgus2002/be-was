package webserver.request;

import webserver.HttpMethod;

import java.util.Collections;
import java.util.Map;

public class HttpRequestMessage {
    private final HttpMethod method;
    private final HttpURL url;
    private final String version;
    private final String body;
    private final Map<String, String> headers;

    public HttpRequestMessage(HttpMethod method, HttpURL url, String version, Map<String, String> headers, String body) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.body = body;
        this.headers = headers;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpURL getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public String getHeader(String key) {
        return Collections.unmodifiableMap(headers).get(key);
    }

    public boolean hasHeader(String key) {
        return Collections.unmodifiableMap(headers).containsKey(key);
    }

    public String getBody() {
        return body;
    }

    public String getRawUrl() {
        return url.getUrl();
    }

    public String getPath() {
        return url.getPath();
    }

    public String getExtension() {
        return url.getExtension();
    }

    public Map<String, String> getParameters() {
        return url.getParameters();
    }
}
