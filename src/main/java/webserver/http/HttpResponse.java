package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String version;
    private int statusCode;
    private String statusText;
    private String contentType;
    private Map<String, String> headers;
    private byte[] body;

    private static final Map<Integer, String> statusMap = new HashMap() {{
        put(200, "OK");
        put(400, "Bad Request");
        put(404, "Not Found");
    }};

    public HttpResponse(HttpResponse.Builder builder) {
        this.version = builder.version();
        this.statusCode = builder.statusCode();
        this.statusText = statusMap.get(statusCode);
        this.contentType = builder.contentType();
        this.headers = builder.headers();
        this.body = builder.body();
    }

    public static class Builder {
        private String version;
        private int statusCode;
        private String contentType = "text/plain";
        private Map<String, String> headers;
        private byte[] body;

        public Builder() {
            this.headers = new HashMap<>();
        }

        public HttpResponse.Builder version(String version) {
            this.version = version;
            return this;
        }

        public HttpResponse.Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public HttpResponse.Builder contentType(String contentType) {
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

        int statusCode() {
            return statusCode;
        }

        String contentType() {
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

    public Map headers() {
        return headers;
    }

    public byte[] body() {
        return this.body;
    }

}
