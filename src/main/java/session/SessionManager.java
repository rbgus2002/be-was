package session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static String createSession(final String userId) {
        Session session = Session.create();
        sessions.put(userId, session);
        return session.getSessionId();
    }

    public static boolean verifySession(final String userId, final String sessionId) {
        Session session = sessions.get(userId);
        return session != null && !session.verify(sessionId);
    }
}
