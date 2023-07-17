package webserver.myframework.requesthandler;

import webserver.http.HttpMethod;

import java.util.Objects;


public class RequestInfoImpl extends RequestInfo {
    private final String uri;
    private final HttpMethod httpMethods;

    public RequestInfoImpl(String uri, HttpMethod httpMethods) {
        this.uri = uri;
        this.httpMethods = httpMethods;
    }

    @Override
    public boolean isUri(String uri) {
        return this.uri.equals(uri);
    }

    @Override
    public boolean isHttpMethod(HttpMethod httpMethod) {
        return this.httpMethods == httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestInfoImpl that = (RequestInfoImpl) o;
        return Objects.equals(uri, that.uri) && httpMethods == that.httpMethods;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, httpMethods);
    }
}
