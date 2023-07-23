package webserver.http.message;

import java.util.Arrays;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final URL url;
    private final HttpVersion httpVersion;
    private final HttpHeaders httpHeaders;
    private final char[] body;

    public HttpRequest(HttpMethod httpMethod, URL url, HttpVersion httpVersion, HttpHeaders httpHeaders, char[] body) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.httpVersion = httpVersion;
        this.httpHeaders = httpHeaders;
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

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public char[] getBody() {
        return body;
    }

    public boolean containsHeader(String header) {
        return httpHeaders.contains(header);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "httpMethod=" + httpMethod +
                ", url=" + url +
                ", httpVersion=" + httpVersion +
                ", httpHeaders=" + httpHeaders +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
