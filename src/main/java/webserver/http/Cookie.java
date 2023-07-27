package webserver.http;

import java.util.Map;

public class Cookie {
    private Map<String, String> attributes;

    public Cookie(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getSessionId() {
        return attributes.getOrDefault("sid", null);
    }
}
