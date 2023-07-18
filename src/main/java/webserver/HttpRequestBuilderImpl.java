package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilderImpl implements HttpRequest.Builder {
    private String method;
    private String uri;
    private String path;
    private String version;
    private Map<String, String> headers;
    private Map<String, String> body;

    public HttpRequestBuilderImpl() {
        this.headers = new HashMap<>();
        this.body = new HashMap<>();
    }

    @Override
    public HttpRequest.Builder uri(String uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public HttpRequest.Builder path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public HttpRequest.Builder version(String version) {
        this.version = version;
        return this;
    }

    @Override
    public HttpRequest.Builder setHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    @Override
    public HttpRequest.Builder method(String method) {
        this.method = method;
        return this;
    }

    @Override
    public HttpRequest build() {
        return new HttpRequestImpl(this);
    }

    String uri() { return  uri; }

    String path() { return path; }

    String method() { return method; }

    String version() { return version; }

    Map headers() { return headers; }

    Map body() { return body; }

}
