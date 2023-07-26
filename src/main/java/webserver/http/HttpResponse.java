package webserver.http;

import webserver.http.enums.ContentType;
import webserver.http.enums.HttpResponseStatus;

import java.util.Set;

import static webserver.http.enums.ContentType.PLAIN;

public class HttpResponse {
    private String version;
    private int statusCode;
    private String statusText;
    private ContentType contentType;
    private HttpHeaders headers;
    private byte[] body;

    private HttpResponse(String version, HttpResponseStatus status,
                         ContentType contentType, HttpHeaders headers, byte[] body) {
        this.version = version;
        this.statusCode = status.getStatusCode();
        this.statusText = status.getStatusText();
        this.contentType = contentType;
        this.headers = headers;
        this.body = body;
    }

    public static class Builder {
        private String version;
        private HttpResponseStatus status;
        private ContentType contentType = PLAIN;
        private HttpHeaders headers;
        private byte[] body = new byte[0];

        public Builder() {
            this.headers = new HttpHeaders();
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

        public HttpResponse.Builder setHeader(String header, String value) {
            this.headers.setHeader(header, value);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(version, status, contentType, headers, body);
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

    public ContentType contentType() {
        return this.contentType;
    }

    public Set<String> headers() {
        return headers.headers();
    }

    public String getHeader(String header) {
        return headers.getHeader(header);
    }

    public byte[] body() {
        return this.body;
    }

}
