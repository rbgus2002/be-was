package webserver.http.message;

import java.util.Arrays;

public enum HttpVersion {
    V1_0("HTTP/1.0"), V1_1("HTTP/1.1"), NOT_IMPLEMENTED("???");

    final String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion from(String version) {
        return Arrays.stream(HttpVersion.values())
                .filter(httpVersion -> httpVersion.version.equals(version))
                .findFirst()
                .orElse(NOT_IMPLEMENTED);
    }

    public String getVersion() {
        return version;
    }
}
