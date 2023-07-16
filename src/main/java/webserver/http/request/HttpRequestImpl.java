package webserver.http.request;

import webserver.http.HttpHeaders;
import webserver.http.HttpMethod;

import java.util.Set;

public class HttpRequestImpl extends HttpRequest {
    private final HttpMethod httpMethod;
    private final String uri;
    private final String version;
    private final HttpHeaders headers;

    public HttpRequestImpl(HttpMethod httpMethod,
                           String uri,
                           String version,
                           HttpHeaders headers) {
            this.httpMethod = httpMethod;
            this.uri = uri;
            this.version = version;
            this.headers = headers;
    }

    @Override
    public HttpMethod getMethod() {
        return httpMethod;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Set<String> getHeaderNames() {
        return headers.getHeaderNames();
    }

    @Override
    public String getHeader(String headerName) {
        return headers.getHeaderValues(headerName);
    }

    @Override
    public Object getBody() {
        return null;
    }
}
