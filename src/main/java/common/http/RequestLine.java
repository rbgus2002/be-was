package common.http;

import common.enums.ContentType;
import common.enums.RequestMethod;

import java.util.Map;

public class RequestLine {
    private final RequestMethod requestMethod;
    private final String path;
    private final String version;
    private final ContentType contentType;
    private final Map<String, String> params;

    public RequestLine(RequestMethod requestMethod, String path, String version, ContentType contentType, Map<String, String> params) {
        this.requestMethod = requestMethod;
        this.path = path;
        this.version = version;
        this.contentType = contentType;
        this.params = params;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
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