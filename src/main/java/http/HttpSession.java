package http;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {
    private final Map<String, Object> attributes = new ConcurrentHashMap<>();
    private final String sid;

    public HttpSession(String sid) {
        this.sid = sid;
    }

    public static HttpSession create() {
        String sid = UUID.randomUUID().toString();
        return new HttpSession(sid);
    }

    public String getSid() {
        return sid;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

}
