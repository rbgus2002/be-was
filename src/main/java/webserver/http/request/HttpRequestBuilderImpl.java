package webserver.http.request;

import webserver.http.HttpHeaders;
import webserver.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilderImpl implements HttpRequest.Builder {
    private HttpMethod method = HttpMethod.GET;
    private String uri = "/";
    private String version = "1.1";
    private final HttpHeaders headers = new HttpHeaders();
    private final Map<String, String> requestParameters = new HashMap<>();

    @Override
    public HttpRequest.Builder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public HttpRequest.Builder uri(String uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public HttpRequest.Builder version(String version) {
        this.version = version;
        return this;
    }

    @Override
    public HttpRequest.Builder addHeader(String headerName, String value) {
        headers.addHeader(headerName, value);
        return this;
    }

    @Override
    public HttpRequest.Builder addParameter(String parameterName, String value) {
        requestParameters.put(parameterName, value);
        return this;
    }

    @Override
    public HttpRequest build() {
        return new HttpRequestImpl(method, uri, version, headers, requestParameters);
    }
}
