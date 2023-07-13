package webserver.http;

public class HttpRequestBuilderImpl implements HttpRequest.Builder {
    private HttpMethod method;
    private String uri;
    private String version;
    private HttpHeaders headers;

    public HttpRequestBuilderImpl() {
        this.headers = new HttpHeaders();
    }

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
    public HttpRequest build() {
        return new HttpRequestImpl(method, uri, version, headers);
    }
}
