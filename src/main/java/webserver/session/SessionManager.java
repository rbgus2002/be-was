package webserver.session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public Session getSession(String sessionId) {
        if (sessionId == null || !sessions.containsKey(sessionId)) {
            return addNewSession();
        }
        return sessions.get(sessionId);
    }

    public Session addNewSession() {
        String sessionId = UUID.randomUUID().toString();
        while (sessions.containsKey(sessionId)){
            sessionId = UUID.randomUUID().toString();
        }
        Session session = new Session(sessionId);
        sessions.put(sessionId, session);
        return session;
    }

    public boolean contains(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public void remove(String sessionId) {
        sessions.remove(sessionId);
    }
}
