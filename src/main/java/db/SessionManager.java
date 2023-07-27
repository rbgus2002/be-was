package db;

import com.google.common.collect.Maps;
import http.Session;
import model.User;

import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<String, Session> sessions = Maps.newConcurrentMap();

    private SessionManager() {
    }

    public static String createSession(String key, Object value) {
        String sid = generateSessionId();
        sessions.put(sid, Session.of(key, value));
        return sid;
    }

    public static String createUserSession(User user) {
        String sid = generateSessionId();
        sessions.put(sid, Session.of("user", user));
        return sid;
    }

    public static Session fetchSession(String sid) {
        Session session = sessions.get(sid);
        if (session != null) {
            session.updateLastAccessTime();
        }
        return session;
    }

    public static User fetchUser(String sid) {
        Session session = sessions.get(sid);
        if (session == null) {
            return null;
        }
        session.updateLastAccessTime();
        return (User) session.getValue("user");
    }

    private static String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
