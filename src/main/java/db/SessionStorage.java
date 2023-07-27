package db;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class SessionStorage {

    private static Map<String, String> sessionIds = Maps.newConcurrentMap();

    public static void addSessionId(String sessionId, String userId) {
        sessionIds.put(sessionId, userId);
    }

    public static void deleteSessionId(String sessionId) {
        sessionIds.remove(sessionId);
    }

    public static String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public static Collection<String> findAllSessionIds() {
        return sessionIds.keySet();
    }

    public static String findUserIdBySessionId(String sessionId) {
        return sessionIds.get(sessionId);
    }

    public static boolean isSessionValid(String sessionId) {
        return findAllSessionIds().stream().anyMatch(id -> id.equals(sessionId));
    }

    public static void clearSessionIds() {
        sessionIds.clear();
    }
}
