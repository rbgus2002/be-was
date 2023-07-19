package common;

import java.util.Map;

public class RequestLine {
    private final Method method;
    private final String path;
    private final String version;
    private final Map<String, String> params;

    public RequestLine(Method method, String path, String version, Map<String, String> params) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.params = params;
    }

    public Method getMethod() {
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