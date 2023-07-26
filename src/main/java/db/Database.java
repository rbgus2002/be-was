package db;

import model.Session;
import model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Database {

    private static final Map<String, User> users = new ConcurrentHashMap<>();
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static void clear() {
        users.clear();
        sessions.clear();
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void addSession(Session session) {
        sessions.put(session.getSessionId(), session);
    }

    public static Session findSessionById(String sessionId) {
        return sessions.get(sessionId);
    }

    public static List<Session> findAllSession() {
        return new ArrayList<>(sessions.values());
    }

}
