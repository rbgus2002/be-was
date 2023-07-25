package http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {
    private static final Map<String, Object> attributes = new ConcurrentHashMap<>();
    private final String sid;

    public HttpSession(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public void addAttribute(Object object) {
        attributes.put(sid, object);
    }

    public Object getAttribute() {
        return attributes.get(sid);
    }
}
