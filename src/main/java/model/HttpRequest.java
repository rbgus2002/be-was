package model;

import dto.UserFormRequestDto;
import model.enums.Method;

public class HttpRequest {
    private final RequestUri requestUri;
    private final String protocol;
    private final Method method;
    private final HttpHeader httpHeader;
    private final String body;

    public static class Builder {
        private RequestUri requestUri;
        private String protocol;
        private Method method;
        private HttpHeader httpHeader;
        private String body;

        public Builder() {
        }

        public Builder requestUri(RequestUri uri) {
            this.requestUri = uri;
            return this;
        }

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder httpHeader(HttpHeader header) {
            this.httpHeader = header;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }

    public HttpRequest(Builder builder) {
        requestUri = builder.requestUri;
        method = builder.method;
        protocol = builder.protocol;
        httpHeader = builder.httpHeader;
        body = builder.body;
    }

    public boolean match(Method method, String uri) {
        return this.method == method && this.requestUri.match(uri);
    }

    public boolean match(Method method) {
        return this.method == method;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public boolean isUriStaticFile() {
        return requestUri.isUriStaticFile();
    }

    public String getUri() {
        return requestUri.getUri();
    }

    //지워질 예정
    public UserFormRequestDto paramsToDto() {
        return this.requestUri.paramsToDto();
    }
}
