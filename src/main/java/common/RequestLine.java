package common;

import common.HttpRequest;

import java.util.Map;

public class RequestLine {
    private final HttpRequest.Method method;
    private final String path;
    private final String version;
    private final Map<String, String> params;

    public RequestLine(HttpRequest.Method method, String path, String version, Map<String, String> params) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.params = params;
    }

    public HttpRequest.Method getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getParams() {
        return params;
    }
}