package webserver.http.message;

import java.util.Arrays;

import static webserver.http.message.HttpHeaders.COOKIE;

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

    public Cookie getCookie() {
        if (httpHeaders.contains(COOKIE)) {
            String cookieString = httpHeaders.getSingleValue(COOKIE);
            return Cookie.from(cookieString);
        }
        return Cookie.empty();
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

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public static class HttpRequestBuilder {
        public HttpMethod httpMethod;
        public URL url;
        public HttpVersion httpVersion;
        public HttpHeaders httpHeaders;
        public char[] body;

        public HttpRequestBuilder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public HttpRequestBuilder url(URL url) {
            this.url = url;
            return this;
        }

        public HttpRequestBuilder httpVersion(HttpVersion httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public HttpRequestBuilder httpHeader(HttpHeaders headers) {
            this.httpHeaders = headers;
            return this;
        }

        public HttpRequestBuilder body(char[] body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(httpMethod, url, httpVersion, httpHeaders, body);
        }
    }
}
