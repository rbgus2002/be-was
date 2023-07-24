package webserver.session;

import java.util.concurrent.CopyOnWriteArrayList;

public final class SessionManager {
    private static final CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();

    private SessionManager() {
    }

    public static void addSession(Session session) {
        sessions.add(session);
    }

    public static boolean verifySessionId(String sessionId) {
        return sessions.stream().anyMatch(session -> session.verifySessionId(sessionId));
    }

    public static String verifySessionIdAndGetUserId(String sessionId) {
        return sessions.stream()
                .filter(session -> session.verifySessionId(sessionId))
                .findFirst()
                .map(Session::getUserId)
                .orElse("");
    }
}
