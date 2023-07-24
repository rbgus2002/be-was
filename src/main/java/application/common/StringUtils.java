package application.common;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StringUtils {
    private static final String REGEX = "\\?";
    private static final String ENTRY_DELIMITER = "=";

    public static Map<String, String> extractText(final String body) {
        return Arrays.stream(body.split(REGEX))
                .map(entry -> entry.split(ENTRY_DELIMITER))
                .filter(strings -> strings.length > 2)
                .collect(Collectors.toMap(strings -> strings[0], strings -> strings[1]));
    }
}
