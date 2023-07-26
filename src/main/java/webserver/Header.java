package webserver;

import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Header {

    private final Map<String, String> headers = new HashMap<>();

    public Header appendHeader(String key, String value) {
        if (headers.containsKey(key)) {
            throw new RuntimeException("이미 존재하는 키입니다.");
        }
        headers.put(key, value);
        return this;
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
