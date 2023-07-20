package model;

import java.util.Objects;

public class HttpMethod {
    public static final HttpMethod GET = new HttpMethod("GET");
    public static final HttpMethod POST = new HttpMethod("POST");
    public static final HttpMethod PUT = new HttpMethod("PUT");
    public static final HttpMethod DELETE = new HttpMethod("DELETE");
    public static final HttpMethod PATCH = new HttpMethod("PATCH");
    public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS");
    public static final HttpMethod HEAD = new HttpMethod("HEAD");
    public static final HttpMethod TRACE = new HttpMethod("TRACE");
    public static final HttpMethod CONNECT = new HttpMethod("CONNECT");



    private final String name;
    private static final HttpMethod[] values = new HttpMethod[] {GET, PATCH, PUT, POST, DELETE, OPTIONS, HEAD, TRACE};

    private HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod valueOf(String name) {
        return new HttpMethod(name);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpMethod that = (HttpMethod) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
