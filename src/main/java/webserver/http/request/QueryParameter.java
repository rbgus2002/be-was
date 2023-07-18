package webserver.http.request;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParameter {
    public static final String DELIMITER = "?";
    private static final String ENTRY_SEPARATOR = "&";
    private static final String ENTRY_DELIMITER = "=";

    private final Map<String, String> map;

    private QueryParameter(final Map<String, String> map) {
        this.map = map;
    }

    public static QueryParameter from(final String text) {
        String queryParameterText = text.substring(text.indexOf(DELIMITER) + 1);
        Map<String, String> map = Arrays.stream(queryParameterText.split(ENTRY_SEPARATOR))
                .map(entry -> entry.split(ENTRY_DELIMITER))
                .collect(Collectors.toMap(strings -> decode(strings[0]), strings -> decode(strings[1])));
        return new QueryParameter(map);
    }

    private static String decode(final String string) {
        return URLDecoder.decode(string, StandardCharsets.UTF_8);
    }

    public String get(final String key) {
        return map.get(key);
    }
}
