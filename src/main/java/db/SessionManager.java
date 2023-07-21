package db;

import model.User;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final String SESSION_COOKIE_NAME = "mySessionId";
    private final Map<String, User> store = new ConcurrentHashMap<>();

    public void createSession(User user, HTTPServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        store.put(sessionId, user);
        response.setHeader("Set-Cookie", SESSION_COOKIE_NAME + "=" + sessionId);
    }

    public User getSession(HTTPServletRequest request) {
        String sessionId = request.getHeader("Cookie");
        if (sessionId == null) {
            throw new IllegalArgumentException("없는 쿠키입니다.");
        }
        return store.get(sessionId);
    }

}
