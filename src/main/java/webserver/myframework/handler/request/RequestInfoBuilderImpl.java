package webserver.myframework.handler.request;

import webserver.http.HttpMethod;


public class RequestInfoBuilderImpl implements RequestInfo.Builder {
    private String uri = "/";
    private HttpMethod httpMethod = HttpMethod.GET;

    @Override
    public RequestInfo.Builder uri(String uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public RequestInfo.Builder httpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    @Override
    public RequestInfo build() {
        return new RequestInfoImpl(this.uri, this.httpMethod);
    }
}
