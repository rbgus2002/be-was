package session;

import model.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserSession {
    private static final Map<String, User> userSessionStorage = new ConcurrentHashMap<>();

    public static void addUser(User user) {
        userSessionStorage.put(createSessionId(), user);
    }

    private static String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public static User getUser(String sessionId) {
        return userSessionStorage.get(sessionId);
    }
}
