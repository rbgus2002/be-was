package http;

import java.util.Map;

public class HttpRequest {
    private String method;
    private String uri;
    private String version;
    private Map<String, String> headers;
    private String sessionId;
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
        this.sessionId = parseSessionId();
        this.body = builder.body;
    }

    private String parseSessionId() {
        String[] sid = headers.get("Cookie").split("SID=");
        if (sid.length != 2)
            return "";
        return sid[sid.length - 1];
    }

    public String getMethod() {
        return this.method;
    }

    public String getUri() {
        return this.uri;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getBody() {
        return this.body;
    }

}
