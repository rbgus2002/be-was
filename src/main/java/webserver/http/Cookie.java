package webserver.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static support.utils.StringUtils.*;

public class Cookie {

    private final Map<String, String> options;
    private final int INDEX_ADJUSTMENT = 1;

    private Cookie(Map<String, String> options) {
        this.options = options;
    }

    public Cookie of(final String string) {
        String[] tokens = string.split(SEMICOLON);

        HashMap<String, String> options = new HashMap<>();

        Arrays.stream(tokens)
                .map(token -> token.split(EQUAL))
                .forEach(option -> options.put(option[0].trim(), option[1].trim()));

        return new Cookie(options);
    }

    public Cookie createEmpty() {
        Map<String, String> options = new HashMap<>();
        return new Cookie(options);
    }

    public void addElement(final String key, final String value) {
        options.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Set-Cookie:");
        options.entrySet().stream()
                .map(entry -> SPACE + entry.getKey() + EQUAL + entry.getValue() + SEMICOLON)
                .forEach(sb::append);
        sb.deleteCharAt(sb.length() - INDEX_ADJUSTMENT);
        sb.append(NEWLINE);
        return sb.toString();
    }
}
