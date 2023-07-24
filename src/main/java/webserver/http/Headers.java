package webserver.http;

import static webserver.http.Http.HEADER_SEPARATOR;
import static webserver.http.Http.LINE_DELIMITER;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import webserver.http.Http.MIME;

public class Headers {
    private final Map<String, String> map;

    public Headers(final Map<String, String> map) {
        this.map = map;
    }

    public static Headers create(final MIME mime) {
        return create(mime, 0);
    }

    public static Headers create(final MIME mime, final int length) {
        Map<String, String> result = new HashMap<>();
        result.put(Http.Headers.CONTENT_TYPE.getName(), mime.getType());
        if (length > 0) {
            result.put(Http.Headers.CONTENT_LENGTH.getName(), String.valueOf(length));
        }
        return new Headers(result);
    }

    public boolean containsKey(final String key) {
        return map.containsKey(key);
    }

    public String get(final Http.Headers header) {
        return map.get(header.getName());
    }

    @Override
    public String toString() {
        return this.map.entrySet().stream()
                .map(entry -> entry.getKey() + HEADER_SEPARATOR + entry.getValue())
                .sorted()
                .collect(Collectors.joining(LINE_DELIMITER));
    }
}
