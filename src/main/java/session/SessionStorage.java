package session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStorage {
    private static final ConcurrentHashMap<UUID, SessionValue> sessionStorage = new ConcurrentHashMap<>();

    public static String setSession(String userId) {
        UUID sessionId = UUID.randomUUID();
        SessionValue sessionValue = new SessionValue(userId);
        sessionStorage.put(sessionId, sessionValue);
        return sessionId.toString();
    }

    //todo getSession해서 책임 분리
    public static String getUserId(UUID sessionId) {
        SessionValue sessionValue = sessionStorage.get(sessionId);
        if(sessionValue == null) {
            return null;
        }
        if(sessionValue.isExpired()) {
            return null;
        }
        return sessionValue.getUserId();
    }
}
