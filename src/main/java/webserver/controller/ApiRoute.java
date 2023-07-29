package webserver.controller;

import webserver.http.constant.HttpMethod;

import java.util.Objects;

public class ApiRoute {
    private String path;
    private HttpMethod method;

    public ApiRoute(String path, HttpMethod method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiRoute pathAndMethod = (ApiRoute) o;
        return Objects.equals(path, pathAndMethod.path) && method == pathAndMethod.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
