package db;

import model.Post;
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
    private static final Map<Long, Post> posts = new ConcurrentHashMap<>();

    static {
        User awdcgy = new User("awdcgy", "awdcgy", "awdcgy", "awdcgy@hash.hash");
        addUser(awdcgy);
    }

    public static void clear() {
        users.clear();
        sessions.clear();
        posts.clear();
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

    public static void addPost(Long id, Post post) {
        posts.put(id, post);
    }

    public static List<Post> findAllPost() {
        return new ArrayList<>(posts.values());
    }

    public static Post findPostById(Long id) {
        return posts.get(id);
    }

    public static void deleteById(String sessionId) {
        sessions.remove(sessionId);
    }
}
