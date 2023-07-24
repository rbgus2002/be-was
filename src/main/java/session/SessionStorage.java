package session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    static final ConcurrentHashMap<UUID, Session> sessionStorage = new ConcurrentHashMap<>();

    public static String setSession(String userId) {
        UUID sessionId = UUID.randomUUID();
        Session session = new Session(userId);
        sessionStorage.put(sessionId, session);
        return sessionId.toString();
    }

    public static String getUserId(UUID sessionId) {
        Session session = sessionStorage.get(sessionId);
        if(!session.isExpired()) {
            return null;
        }
        return session.getUserId();
    }
}
