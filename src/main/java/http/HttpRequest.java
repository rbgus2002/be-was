package http;

import java.util.Map;

import static http.HttpMethod.GET;

public class HttpRequest {
    private String method = GET.getMethod();
    private String uri;
    private String version;
    private Map<String, String> headers;
    private String body = "";

    public static class RequestBuilder {
        private String bMethod = GET.getMethod();
        private String bUri;
        private String bVersion;
        private Map<String, String> bHeaders;
        private String bBody = "";

        public RequestBuilder(String method, String uri, String version) {
            bMethod = method;
            bUri = uri;
            bVersion = version;
        }

        public RequestBuilder setHeader(Map<String, String> headers) {
            bHeaders = headers;
            return this;
        }

        public RequestBuilder setBody(String body) {
            bBody = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }

    }


    public HttpRequest(RequestBuilder builder) {
        this.method = builder.bMethod;
        this.uri = builder.bUri;
        this.version = builder.bVersion;
        this.headers = builder.bHeaders;
        this.body = builder.bBody;
    }

    public String getUri() {
        return this.uri;
    }

}
