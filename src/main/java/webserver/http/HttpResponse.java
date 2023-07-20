package webserver.http;

import webserver.http.enums.ContentType;
import webserver.http.enums.HttpResponseStatus;

import java.util.HashMap;
import java.util.Map;

import static webserver.http.enums.ContentType.PLAIN;

public class HttpResponse {
    private String version;
    private int statusCode;
    private String statusText;
    private String contentType;
    private Map<String, String> headers;
    private byte[] body;

    public HttpResponse(HttpResponse.Builder builder) {
        this.version = builder.version();
        this.statusCode = builder.status().getStatusCode();
        this.statusText = builder.status().getStatusText();
        this.contentType = builder.contentType().getContentType();
        this.headers = builder.headers();
        this.body = builder.body();
    }

    public static class Builder {
        private String version;
        private HttpResponseStatus status;
        private ContentType contentType = PLAIN;
        private Map<String, String> headers;
        private byte[] body = new byte[0];

        public Builder() {
            this.headers = new HashMap<>();
        }

        public HttpResponse.Builder version(String version) {
            this.version = version;
            return this;
        }

        public HttpResponse.Builder status(HttpResponseStatus status) {
            this.status = status;
            return this;
        }

        public HttpResponse.Builder contentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public HttpResponse.Builder body(byte[] body) {
            this.body = body;
            return this;
        }

        public HttpResponse.Builder setHeader(String name, String value) {
            this.headers.put(name, value);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }

        String version() {
            return version;
        }

        HttpResponseStatus status() {
            return status;
        }

        ContentType contentType() {
            return contentType;
        }

        Map headers() {
            return headers;
        }

        byte[] body() {
            return body;
        }


    }

    public static HttpResponse.Builder newBuilder() {
        return new Builder();
    }

    public String version() {
        return this.version;
    }

    public int statusCode() {
        return this.statusCode;
    }

    public String statusText() {
        return this.statusText;
    }

    public String contentType() { return this.contentType; }

    public Map<String, String> headers() {
        return headers;
    }

    public byte[] body() {
        return this.body;
    }

}
