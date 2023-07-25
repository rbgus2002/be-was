package db;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class SessionDatabase {

    private static Map<String, String> sessionIds = Maps.newConcurrentMap();

    public static void addSessionId(String sessionId, String userId) {
        sessionIds.put(sessionId, userId);
    }

    public static void deleteSessionId(String userId) {
        sessionIds.remove(userId);
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

    public static void clearSessionIds() {
        sessionIds.clear();
    }
}
