package session;

import model.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserSession {
    private static final Map<String, User> userSession = new ConcurrentHashMap<>();

    public static void addUser(User user) {
        userSession.put(createSessionId(), user);
    }

    private static String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public static User getUser(String sessionId) {
        return userSession.get(sessionId);
    }
}
