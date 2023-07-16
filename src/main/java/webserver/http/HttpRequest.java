package webserver.http;


import java.util.Set;

public abstract class HttpRequest {
    public abstract HttpMethod getMethod();
    public abstract String getUri();
    public abstract String getVersion();
    public abstract Set<String> getHeaderNames();
    public abstract String getHeader(String headerName);
    //TODO: Object 나중에 바꿀 것
    public abstract Object getBody();
    public static Builder builder() {
        return new HttpRequestBuilderImpl();
    }

    public interface Builder {
        Builder method(HttpMethod httpMethod);
        Builder uri(String uri);
        Builder version(String version);
        Builder addHeader(String headerName, String value);
        HttpRequest build();
    }
}
