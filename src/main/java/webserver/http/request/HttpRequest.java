package webserver.http.request;


import webserver.http.HttpMethod;
import webserver.myframework.session.Session;
import webserver.myframework.session.SessionManager;

import java.util.Optional;
import java.util.Set;

public abstract class HttpRequest {
    public abstract HttpMethod getMethod();

    public abstract String getUri();

    public abstract String getVersion();

    public abstract Set<String> getHeaderNames();

    public abstract String getHeader(String headerName);

    public abstract Optional<String> getParameter(String parameterName);

    public abstract byte[] getBody();

    public abstract String getBodyToString();

    public abstract Session getSession();

    public abstract Session getSession(boolean create);
    
    public static Builder builder(SessionManager sessionManager) {
        return new HttpRequestBuilderImpl(sessionManager);
    }

    public interface Builder {
        Builder method(HttpMethod httpMethod);

        Builder uri(String uri);

        Builder version(String version);

        Builder addHeader(String headerName, String value);

        @SuppressWarnings("UnusedReturnValue")
        Builder addParameter(String parameterName, String value);

        Builder body(byte[] body);

        HttpRequest build();
    }
}
