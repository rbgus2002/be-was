package db;

import com.google.common.collect.Maps;
import http.Session;

import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<String, Session> sessions = Maps.newConcurrentMap();

    private SessionManager() {
    }

    public static String createSession(Object obj){
        String sid = UUID.randomUUID().toString();
        sessions.put(sid, Session.of(sid, obj));
        return sid;
    }

    public static Session fetchSession(String sid){
        Session session = sessions.get(sid);
        if(session != null){
            session.updateLastAccessTime();
        }
        return session;
    }
}
