package global.constant;

import exception.BadRequestException;

import java.util.Arrays;

public enum HttpMethod {
    GET("GET"), POST("POST");

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }

    public static HttpMethod findHttpMethod(String methodName) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.method.equalsIgnoreCase(methodName))
                .findFirst()
                .orElseThrow(BadRequestException::new);
    }
}