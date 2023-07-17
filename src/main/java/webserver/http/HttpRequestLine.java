package webserver.http;

import utils.RequestParser;

import java.util.Map;

public class HttpRequestLine {
    private final String method;
    private final String uri;
    private final String version;

    public HttpRequestLine(String method, String uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    public static HttpRequestLine create(String requestLine) {
        Map<String, String> requestLines = RequestParser.parseRequestLine(requestLine);
        return new HttpRequestLine(
                requestLines.get("method"),
                requestLines.get("uri"),
                requestLines.get("version")
        );
    }
}
