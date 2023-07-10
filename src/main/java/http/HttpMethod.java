package http;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST, DELETE, PUT, PATCH, OPTIONS;

    public static HttpMethod of(String method) {
        return Arrays.stream(HttpMethod.values())
                .filter(value -> value.name().equalsIgnoreCase(method))
                .findAny().orElseThrow(() -> new IllegalArgumentException("유효하지 않은 HTTP 메소드입니다."));
    }
}
