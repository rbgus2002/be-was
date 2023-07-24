package db;

import com.google.common.collect.Maps;
import model.Post;
import model.Session;
import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static final Map<String, User> users = Maps.newConcurrentMap();
    private static final Map<String, Session> sessions = Maps.newConcurrentMap();
    private static final Map<Integer, Post> posts = Maps.newConcurrentMap();

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
    public static void deleteAllUser() {
        users.clear();
    }

    // Session
    public static Session addSession(String userId) {
        Session session = new Session(userId);
        sessions.put(userId, session);
        return session;
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
    public static void deleteAllSession() {
        sessions.clear();
    }

    // Post
    public static Post addPost(String userId, String title, String content) {
        Post post = new Post(userId, title, content);
        posts.put(post.getPostId(), post);
        return post;
    }
    public static Post findPostByPostId(Integer postId) {
        return posts.get(postId);
    }
    public static Collection<Post> findAllPost() {
        return posts.values();
    }
    public static void updatePost(Integer postId, Post post) {
        posts.put(postId, post);
    }
    public static void deleteAllPost() {
        posts.clear();
    }
}
