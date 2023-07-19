package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String method;
    private String uri;
    private String path;
    private String version;
    private String contentType = "text/plain";
    private Map<String, String> headers;
    private Map<String, String> body;

    HttpRequest(HttpRequest.Builder builder) {
        this.method = builder.method();
        this.uri = builder.uri();
        this.path = builder.path();
        this.version = builder.version();
        this.contentType = builder.contentType();
        this.headers = builder.headers();
        this.body = builder.headers();
    }

    public static class Builder {
        private String method;
        private String uri;
        private String path;
        private String version;
        private String contentType = "text/plain";
        private Map<String, String> headers;
        private Map<String, String> body;

        public Builder() {
            this.headers = new HashMap<>();
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

        // TODO: header 전체 파싱? headers로 나눠서 header 각자 파싱하도록
        public HttpRequest.Builder setHeader(String name, String value) {
            if (name.equals("Accept")) {
                this.contentType = value.split("[,;]")[0];
            }
            this.headers.put(name, value);
            return this;
        }

        public HttpRequest.Builder method(String method) {
            this.method = method;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }

        String uri() {
            return uri;
        }

        String path() {
            return path;
        }

        String method() {
            return method;
        }

        String version() {
            return version;
        }

        String contentType() {
            return contentType;
        }

        Map headers() {
            return headers;
        }

        Map body() {
            return body;
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

    public Map headers() {
        return headers;
    }

    public Map body() {
        return body;
    }
}
