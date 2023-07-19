package webserver.myframework.requesthandler;

import webserver.http.HttpMethod;

public abstract class RequestInfo {
    public abstract boolean isUri(String uri);

    public abstract boolean isHttpMethod(HttpMethod httpMethod);

    public static Builder builder() {
        return new RequestInfoBuilderImpl();
    }

    public interface Builder {
        Builder uri(String uri);
        Builder httpMethod(HttpMethod httpMethod);
        RequestInfo build();
    }
}
