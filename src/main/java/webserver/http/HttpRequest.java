package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String uri;
    private String path;
    private String version;
    private String contentType = "text/plain";
    private HttpHeaders headers;
    private Map<String, String> body;

    private HttpRequest(String method, String uri, String path, String version,
                        String contentType, HttpHeaders headers, Map<String, String> body) {
        this.method = method;
        this.uri = uri;
        this.path = path;
        this.version = version;
        this.contentType = contentType;
        this.headers = headers;
        this.body = body;
    }

    public static class Builder {
        private String method;
        private String uri;
        private String path;
        private String version;
        private String contentType = "text/plain";
        private HttpHeaders headers;
        private Map<String, String> body;

        public Builder() {
            this.headers = new HttpHeaders();
            this.body = new HashMap<>();
        }

        public HttpRequest.Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public HttpRequest.Builder path(String path) {
            this.path = path;
            return this;
        }

        public HttpRequest.Builder version(String version) {
            this.version = version;
            return this;
        }

        public HttpRequest.Builder setHeader(String headerString) {
            int splitIndex = headerString.indexOf(":");
            this.headers.setHeader(headerString.substring(0, splitIndex).trim(),
                    headerString.substring(splitIndex + 1).trim());
            return this;
        }

        public HttpRequest.Builder method(String method) {
            this.method = method;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(method, uri, path, version, contentType, headers, body);
        }
    }

    public static HttpRequest.Builder newBuilder() {
        return new Builder();
    }

    public String method() {
        return method;
    }

    public String uri() {
        return uri;
    }

    public String path() {
        return path;
    }

    public String version() {
        return version;
    }

    public String contentType() {
        return contentType;
    }

    public HttpHeaders headers() {
        return headers;
    }

    public String getHeader(String field) {
        return headers.getHeader(field);
    }

    public Map body() {
        return body;
    }
}
