package webserver.controller;

import webserver.http.request.RequestMethod;

import java.util.Objects;

public class ValueAndMethod {
    private final String value;
    private final RequestMethod requestMethod;

    public ValueAndMethod(String value, RequestMethod requestMethod) {
        this.value = value;
        this.requestMethod = requestMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueAndMethod that = (ValueAndMethod) o;
        return Objects.equals(value, that.value) && requestMethod == that.requestMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, requestMethod);
    }
}
