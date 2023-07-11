package util;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
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

    public static void printLog(Logger logger, List<String> strings) {
        for (String str : strings) {
            logger.debug(str);
        }
    }

    public static List<String> convertBufferedReaderToList(BufferedReader reader) throws IOException {
        String line;
        List<String> strings = new ArrayList<>();
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            strings.add(line);
        }
        return strings;
    }
}
