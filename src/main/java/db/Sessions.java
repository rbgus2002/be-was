package db;

import webserver.http.Session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Sessions {
    private static ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();

    public static void addSession(Session session) { sessions.put(session.getSessionId(), session); }

}
