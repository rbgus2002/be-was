package http;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSession {
    private static final Map<String, Object> attributes = new ConcurrentHashMap<>();
    private final String sid;

    public HttpSession(String sid) {
        this.sid = sid;
    }

    public static HttpSession create() {
        String sid = UUID.randomUUID().toString();
        return new HttpSession(sid);
    }

    public static boolean isValid(String sid) {
        return attributes.containsKey(sid);
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

    public boolean isValid() {
        return attributes.containsKey(sid);
    }
}
