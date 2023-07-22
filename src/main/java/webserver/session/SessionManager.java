package webserver.session;

import java.util.concurrent.CopyOnWriteArrayList;

public final class SessionManager {
    private static SessionManager instance;

    private static final CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                instance = new SessionManager();
            }
        }
        return instance;
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public String verifySessionIdAndGetUserId(String sessionId) {
        return sessions.stream()
                .filter(session -> session.verifySessionId(sessionId))
                .findFirst()
                .map(Session::getUserId)
                .orElse("");
    }
}
