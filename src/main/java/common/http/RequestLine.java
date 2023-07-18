package common.http;

import common.enums.ContentType;
import common.enums.Method;

import java.util.Map;

public class RequestLine {
    private final Method method;
    private final String path;
    private final String version;
    private final ContentType contentType;
    private final Map<String, String> params;

    public RequestLine(Method method, String path, String version, ContentType contentType, Map<String, String> params) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.contentType = contentType;
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

    public ContentType getContentType() {
        return contentType;
    }

    public Map<String, String> getParams() {
        return params;
    }
}