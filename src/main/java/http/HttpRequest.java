package http;

import java.util.Map;

public class HttpRequest {
    private String method;
    private String uri;
    private String version;
    private Map<String, String> headers;
    private String body = "";

    public static class RequestBuilder {
        private String method;
        private String uri;
        private String version;
        private Map<String, String> headers;
        private String body = "";

        public RequestBuilder(String method, String uri, String version) {
            this.method = method;
            this.uri = uri;
            this.version = version;
        }

        public RequestBuilder setHeader(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public RequestBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }

    }


    public HttpRequest(RequestBuilder builder) {
        this.method = builder.method;
        this.uri = builder.uri;
        this.version = builder.version;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public String getMethod() {
        return this.method;
    }

    public String getUri() {
        return this.uri;
    }

    public String getBody() {
        return this.body;
    }

}
