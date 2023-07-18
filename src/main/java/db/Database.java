package db;

import com.google.common.collect.Maps;

import model.User;
import model.Session;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static final Map<String, User> users = Maps.newHashMap();
    private static final Map<String, Session> sessions = Maps.newHashMap();

    // User
    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }
    public static User findUserById(String userId) {
        return users.get(userId);
    }
    public static Collection<User> findAllUser() {
        return users.values();
    }

    // Session
    public static void addSession(String userId) {
        sessions.put(userId, new Session(userId));
    }
    public static void updateSession(String userId, Session session) {
        sessions.put(userId, session);
    }
    public static Session findSessionByUserId(String userId) {
        return sessions.get(userId);
    }
    public static Collection<Session> findAllSession() {
        return sessions.values();
    }
}
