package db;

import webserver.http.Session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Sessions {
    private static ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();

    public static void addSession(Session session) {
        sessions.put(session.getSessionId(), session);
    }

    public static Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static boolean hasSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    public static void removeSession(Session session) {
        if(hasSession(session.getSessionId()))
            sessions.remove(session.getSessionId(), session);
    }
}
