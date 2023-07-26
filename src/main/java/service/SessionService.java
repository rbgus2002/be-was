package service;

import db.Sessions;
import webserver.http.Session;

public class SessionService {
    public static void addSession(Session session) {
        Sessions.addSession(session);
    }

    public static Session getSession(String sessionId) {
        return Sessions.getSession(sessionId);
    }

    public static boolean isValidSession(String sessionId) {
        return Sessions.hasSession(sessionId);
    }

    public static void removeSession(Session session) {
        Sessions.removeSession(session);
    }

}
