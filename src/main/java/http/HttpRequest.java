package http;

import exception.BadRequestException;

import java.util.HashMap;
import java.util.Map;

import static exception.ExceptionList.INVALID_URI;
import static http.HttpMethod.GET;
import static utils.StringUtils.decodeBody;

public class HttpRequest {
    private String method;
    private String uri;
    private Map<String, String> queryString;
    private String version;
    private Map<String, String> headers;
    private String sessionId;
    private Map<String, String> body;

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
        this.uri = parseUri(builder.uri);
        this.queryString = parseQueryString(builder.uri);
        this.version = builder.version;
        this.headers = builder.headers;
        this.sessionId = parseSessionId();
        this.body = parseBody(builder.body);
    }

    private String parseUri(String uri) {
        if (uri.contains("?"))
            return uri.split("\\?")[0];
        return uri;
    }

    private Map<String, String> parseQueryString(String uri) {
        if (uri.contains("?") && uri.split("\\?").length == 2)
            return parseBody(uri.split("\\?")[1]);
        return null;
    }

    private String parseSessionId() {
        String[] sid = headers.get("Cookie").split("SID=");
        if (sid.length != 2)
            return "";
        return sid[sid.length - 1];
    }

    private Map<String, String> parseBody(String body) {
        if (body.equals(""))
            return null;
        String[] params = body.split("&");
        Map<String, String> information = new HashMap<>();
        for (String param : params) {
            String[] info = param.split("=");
            if (info.length != 2)
                throw new BadRequestException(INVALID_URI);
            information.put(info[0], decodeBody(info[1]));
        }
        return information;
    }

    public String getMethod() {
        return this.method;
    }

    public String getUri() {
        return this.uri;
    }

    public Map<String, String> getQueryString() {
        return this.queryString;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public Map<String, String> getBody() {
        return this.body;
    }

}
