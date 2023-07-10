package util;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.Objects;
import java.util.Optional;

public class Utils {
    private Utils() {
    }

    public static InputStream getResourceAsStream(URI requestUrl) {
        String[] split = requestUrl.toString().split("\\.");
        String ext = split[split.length - 1];
        return Utils.class.getResourceAsStream((Objects.equals(ext, "html") ? "/templates/" : "/static/") + requestUrl);
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
