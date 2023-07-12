package util;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.Optional;

public class HttpUtils {
    public enum Method {
        GET, POST, DELETE, PUT, PATCH, OPTIONS;

        public static Method of(String method) {
            return Arrays.stream(Method.values())
                    .filter(value -> value.name().equalsIgnoreCase(method))
                    .findAny().orElseThrow(() -> new IllegalArgumentException("유효하지 않은 HTTP 메소드입니다."));
        }
    }

    public static Optional<HttpClient.Version> getHttpVersion(String version) {
        if (version.equals("HTTP/1.1")) {
            return Optional.of(HttpClient.Version.HTTP_1_1);
        } else if (version.equals("HTTP/2.0")) {
            return Optional.of(HttpClient.Version.HTTP_2);
        }
        return Optional.empty();
    }

}
