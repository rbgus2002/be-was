package webserver;

import java.util.HashMap;
import java.util.Map;

public class Cookie {

    private final String key;
    private final String value;
    private final Map<String, String> options = new HashMap<>();

    private Cookie(String key, String value, String path, String expires) {
        this.key = key;
        this.value = value;
        if (path != null) {
            this.options.put("path", path);
        }
        if (expires != null) {
            this.options.put("expires", expires);
        }
    }

    public static class CookieBuilder {

        private String key;
        private String value;
        private String path;
        private String expires;

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

        public CookieBuilder expires(String expires) {
            this.expires = expires;
            return this;
        }

        public Cookie build() {
            return new Cookie(key, value, path, expires);
        }

    }

    public String buildCookie() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(key)
                .append("=")
                .append(value)
                .append("; ");

        options.forEach(
                (key, value) -> {
                    stringBuilder.append(key)
                            .append("=")
                            .append(value)
                            .append("; ");
                }
        );
        return stringBuilder.toString().replaceAll(".$", "");
    }

}
