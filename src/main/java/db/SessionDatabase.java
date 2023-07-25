package db;

import com.google.common.collect.Maps;
import http.HttpSession;

import java.util.Map;

public class SessionDatabase {
    private static final Map<String, HttpSession> sessions = Maps.newConcurrentMap();

    private SessionDatabase() {
    }

    public static HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void addSession(String sessionId, HttpSession session) {
        sessions.put(sessionId, session);
    }
}
