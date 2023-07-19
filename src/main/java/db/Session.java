package db;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Session {

    private static final Map<String, Map<String, Object>> sessions = new ConcurrentHashMap<>();

    public static String createSession() {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new ConcurrentHashMap<>());
        return sessionId;
    }

    public static void setAttribute(String sessionId, String key, String value) {
        Map<String, Object> sessionData = sessions.get(sessionId);
        if (sessionData != null) {
            sessionData.put(key, value);
        }
    }

    public static Object getAttribute(String sessionId, String key) {
        Map<String, Object> sessionData = sessions.get(sessionId);
        if (sessionData != null) {
            return sessionData.get(key);
        }
        return null;
    }

    public static void removeAttribute(String sessionId, String key) {
        Map<String, Object> sessionData = sessions.get(sessionId);
        if (sessionData != null) {
            sessionData.remove(key);
        }
    }

    public static void invalidateSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
