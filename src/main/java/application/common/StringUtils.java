package application.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StringUtils {
    private static final String ENTRY_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    public static Map<String, String> extractBy(final String body) {
        return Arrays.stream(body.split(ENTRY_DELIMITER))
                .map(entry -> entry.split(KEY_VALUE_DELIMITER))
                .filter(strings -> strings.length == 2)
                .collect(Collectors.toMap(strings -> strings[0], strings -> strings[1]));
    }
}
