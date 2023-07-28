package webserver;

import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Header {

    private final Map<String, String> headers = new HashMap<>();
    public static final String Location = "Location";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";

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

    public Header setContentType(String value) {
        return appendHeader(CONTENT_TYPE, value);
    }

    public Header setContentLength(String value) {
        return appendHeader(CONTENT_LENGTH, value);
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
