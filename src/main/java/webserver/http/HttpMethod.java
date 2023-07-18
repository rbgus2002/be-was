package webserver.http;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"), POST("POST"), NOT_IMPLEMENTED("NOT_IMPLEMENTED");

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod from(String method) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.method.equals(method))
                .findFirst()
                .orElse(NOT_IMPLEMENTED);
    }
}
