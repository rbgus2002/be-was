package webserver.http;

import java.util.Arrays;
import java.util.HashMap;

import static support.utils.StringUtils.*;

public class Header {

    private final HashMap<String, String> headers;

    private Header(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public static Header of(final String string) {
        String[] tokens = string.split(NEWLINE);

        HashMap<String, String> headers = new HashMap<>();

        Arrays.stream(tokens)
                .map(token -> token.split(COLON))
                .forEach(header -> headers.put(header[0].trim(), header[1].trim()));

        return new Header(headers);
    }

    public static Header createEmpty() {
        HashMap<String, String> headers = new HashMap<>();
        return new Header(headers);
    }

    public void addElement(final String key, final String value) {
        headers.put(key, value);
    }

    public String getElement(final String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        headers.entrySet().stream()
                .map(entry -> entry.getKey() + COLON + SPACE + entry.getValue() + NEWLINE)
                .forEach(sb::append);
        return sb.toString();
    }
}
