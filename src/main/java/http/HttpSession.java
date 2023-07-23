package http;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {
    private static final Map<String, User> attributes = new HashMap<>();
    private final String sid;

    public HttpSession(String sid) {
        this.sid = sid;
    }

    public void addSession(String sid, User user) {
        attributes.put(sid, user);
    }

    public User getUser() {
        return attributes.get(sid);
    }
}
