package model;

import com.google.common.net.HttpHeaders;
import model.enums.HttpMethod;

import java.util.HashMap;
import java.util.Map;

import static util.StringUtils.*;

public class HttpRequest {
    public static final int UUID_LENGTH = 36;
    public static final int OFFSET = 4;
    public static final String SID = "sid";
    private final RequestUri requestUri;
    private final String protocol;
    private final HttpMethod httpMethod;
    private final HttpHeader httpHeader;
    private final String body;

    public Map<String, String> getBodyMap() {
        Map<String, String> map = new HashMap<>();
        String[] splitByAmpersand = splitBy(body, AMPERSAND_MARK);
        for (var values : splitByAmpersand) {
            String[] splitParam = splitBy(values, EQUAL_MARK);
            if (splitParam.length < 2) continue;

            map.put(splitParam[0], splitParam[1]);
        }

        return map;
    }

    public String getSessionIdInCookie() {
        String cookie = httpHeader.get(HttpHeaders.COOKIE);
        if (cookie == null) return NO_CONTENT;

        int indexOfSid = cookie.indexOf(SID);
        if (indexOfSid == -1) return NO_CONTENT;

        return cookie.substring(indexOfSid + OFFSET, indexOfSid + OFFSET + UUID_LENGTH);
    }

    public static class Builder {
        private RequestUri requestUri;
        private String protocol;
        private HttpMethod httpMethod;
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

        public Builder method(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
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
        httpMethod = builder.httpMethod;
        protocol = builder.protocol;
        httpHeader = builder.httpHeader;
        body = builder.body;
    }

    public boolean match(HttpMethod httpMethod, String uri) {
        return this.httpMethod == httpMethod && this.requestUri.match(uri);
    }

    public boolean match(HttpMethod httpMethod) {
        return this.httpMethod == httpMethod;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public HttpMethod getMethod() {
        return this.httpMethod;
    }

    public boolean isUriStaticFile() {
        return requestUri.isUriStaticFile();
    }

    public String getUri() {
        return requestUri.getUri();
    }

}
