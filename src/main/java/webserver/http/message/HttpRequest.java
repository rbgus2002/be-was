package webserver.http.message;

import java.util.List;
import java.util.Map;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final URL url;
    private final HttpVersion httpVersion;
    private final Map<String, List<String>> metaData;
    private final String body;

    public HttpRequest(HttpMethod httpMethod, URL url, HttpVersion httpVersion, Map<String, List<String>> getMetaData, String body) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.httpVersion = httpVersion;
        this.metaData = getMetaData;
        this.body = body;
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public URL getURL() {
        return url;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public Map<String, List<String>> getMetaData() {
        return metaData;
    }

    public String getBody() {
        return body;
    }
}
