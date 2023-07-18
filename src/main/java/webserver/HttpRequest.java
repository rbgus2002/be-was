package webserver;

import java.util.Map;

public abstract class HttpRequest {

    protected HttpRequest() {}

    public interface Builder {
        public HttpRequest.Builder uri(String uri);
        public HttpRequest.Builder version(String version);
        public HttpRequest.Builder setHeader(String name, String value);
        public HttpRequest.Builder method(String method);
        public HttpRequest build();
    }

    public static HttpRequest.Builder newBuilder() {
        return new HttpRequestBuilderImpl();
    }

    public abstract String method();

    public abstract String uri();

    public abstract String version();

    public abstract Map headers();

    public abstract Map body();
}
