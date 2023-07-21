package webserver.http.request;

import webserver.http.HttpHeaders;
import webserver.http.HttpMethod;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class HttpRequestImpl extends HttpRequest {
    private final HttpMethod httpMethod;
    private final String uri;
    private final String version;
    private final HttpHeaders headers;
    private final Map<String, String> requestParameters;
    private final byte[] body;

    public HttpRequestImpl(HttpMethod httpMethod,
                           String uri,
                           String version,
                           HttpHeaders headers,
                           Map<String, String> requestParameters,
                           byte[] body) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.version = version;
        this.headers = headers;
        this.requestParameters = requestParameters;
        this.body = body;
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
    public Optional<String> getParameter(String parameterName) {
        return Optional.ofNullable(requestParameters.get(parameterName));
    }

    @Override
    public byte[] getBody() {
        return this.body;
    }

    @Override
    public String getBodyToString() {
        return URLDecoder.decode(
                new String(getBody(), StandardCharsets.UTF_8),
                StandardCharsets.UTF_8);
    }
}
