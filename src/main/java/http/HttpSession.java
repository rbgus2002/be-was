package http;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private final String sessionId;
    private final Map<String, Object> attributes;

    public HttpSession(String sessionId) {
        this.sessionId = sessionId;
        this.attributes = new HashMap<>();
    }

    public Object getAttributes(String key) {
        return attributes.get(key);
    }

    public void setAttributes(String key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public String toString() {
        return "HttpSession{" +
                "sessionId='" + sessionId + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
