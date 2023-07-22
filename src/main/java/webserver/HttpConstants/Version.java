package webserver.HttpConstants;

import exception.InvalidVersionException;

import java.util.Arrays;

public enum Version {
    HTTP_0_9("HTTP/0.9"),
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0"),
    HTTP_3_0("HTTP/3.0");

    private final String description;

    Version(String description) {
        this.description = description;
    }

    public static Version of(final String version) {
        return Arrays.stream(Version.values())
                .filter(v -> v.description.equals(version))
                .findFirst()
                .orElseThrow(() -> new InvalidVersionException(version));
    }

    public String getDescription() {
        return this.description;
    }
}
