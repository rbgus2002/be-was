package global.request;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestHeader {
    private final Map<String, String> headers;
    private static final String NEW_LINE = "\r\n";
    private static final String COLUMN_SEPARATOR = ":";

    public RequestHeader(String headerLines) {
        this.headers = parseHeaders(headerLines);
    }

    private Map<String, String> parseHeaders(String lines) {
        return Stream.of(lines.split(NEW_LINE))
                .map(line -> line.split(COLUMN_SEPARATOR))
                .collect(Collectors.toMap(x -> x[0].trim(), x -> x[1].trim()));
    }

    public boolean contains(String key) {
        return headers.containsKey(key);
    }

    public String get(String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }
}