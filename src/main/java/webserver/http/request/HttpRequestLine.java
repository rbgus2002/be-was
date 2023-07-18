package webserver.http;

import utils.RequestParser;

import java.util.Map;

import static utils.StringUtils.NEW_LINE;

public class HttpRequestLine {
    private final String method;
    private final String uri;
    private final String version;

    public HttpRequestLine(String method, String uri, String version) {
        this.method = method;
        this.uri = uri;
        this.version = version;
    }
    public String getUri() {
        return uri;
    }

    public static HttpRequestLine create(String requestLine) {
        Map<String, String> requestLines = RequestParser.parseRequestLine(requestLine);
        return new HttpRequestLine(
                requestLines.get("method"),
                requestLines.get("uri"),
                requestLines.get("version")
        );
    }

    public void show(StringBuilder sb) {
        sb.append("METHOD : ").append(method).append(NEW_LINE);
        sb.append("URI : ").append(uri).append(NEW_LINE);
        sb.append("VERSION : ").append(version).append(NEW_LINE);
    }
}
