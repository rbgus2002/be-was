package webserver.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final Map<String, Object> sessions = new ConcurrentHashMap<>();

    public String addSession(Object object) {
        String sessionId = UUID.randomUUID().toString();
        while (sessions.containsKey(sessionId)){
            sessionId = UUID.randomUUID().toString();
        }
        sessions.put(sessionId, object);
        return sessionId;
    }

    public boolean contains(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public Object find(String sessionId) {
        return sessions.get(sessionId);
    }

    public void remove(String sessionId) {
        sessions.remove(sessionId);
    }
}
