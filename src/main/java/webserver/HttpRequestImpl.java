package webserver;

import java.util.Map;

public class HttpRequestImpl extends HttpRequest {
    private String method;
    private String uri;
    private String version;
    private Map<String, String> headers;
    private Map<String, String> body;

    HttpRequestImpl(HttpRequestBuilderImpl builder) {
        this.method = builder.method();
        this.uri = builder.uri();
        this.version = builder.version();
        this.headers = builder.headers();
        this.body = builder.headers();
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public String uri() {
        return uri;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public Map headers() {
        return headers;
    }

    @Override
    public Map body() {
        return body;
    }
}
