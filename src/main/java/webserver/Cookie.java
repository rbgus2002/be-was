package webserver;

import java.util.HashMap;
import java.util.Map;

public class Cookie {

    private final String key;
    private final String value;
    private final Map<String, String> options = new HashMap<>();

    public Cookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        if (path != null) {
            this.options.put("PATH", path);
        }
    }

    public static class CookieBuilder {

        private String key;
        private String value;
        private String path;

        public CookieBuilder key(String key) {
            this.key = key;
            return this;
        }

        public CookieBuilder value(String value) {
            this.value = value;
            return this;
        }

        public CookieBuilder path(String path) {
            this.path = path;
            return this;
        }

        public Cookie build() {
            return new Cookie(key, value, path);
        }

    }

    public String buildCookie() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(key);
        stringBuilder.append("=");
        stringBuilder.append(value);
        stringBuilder.append("; ");

        options.forEach(
                (key, value) -> {
                    stringBuilder.append(key);
                    stringBuilder.append("=");
                    stringBuilder.append(value);
                    stringBuilder.append("; ");
                }
        );

        int length = stringBuilder.length();
        if (length != 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

}
