package webserver;

import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Header {

    private final Map<String, String> headers = new HashMap<>();
    private static final String Location = "Location";

    private Map<String, String> getHeaders() {
        return headers;
    }

    public Header appendHeader(String key, String value) {
        if (headers.containsKey(key)) {
            throw new RuntimeException("이미 존재하는 키입니다.");
        }
        headers.put(key, value);
        return this;
    }

    public Header appendHeader(Header header) {
        header.getHeaders()
                .forEach(this::appendHeader);
        return this;
    }

    public Header setLocation(String value) {
        return appendHeader(Location, value);
    }

    public String getValue(String key) {
        return headers.get(key);
    }

    public String buildHeader() {
        StringBuilder stringBuilder = new StringBuilder();

        headers.forEach(
                (key, value) -> {
                    stringBuilder.append(key)
                            .append(": ")
                            .append(value)
                            .append(StringUtils.CRLF);
                }
        );

        return stringBuilder.toString();
    }

}
