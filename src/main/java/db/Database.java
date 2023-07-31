package db;

import application.model.User;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static Map<String, User> users = Maps.newConcurrentMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        }
        throw new IllegalArgumentException("해당 userId가 존재하지 않습니다.");
    }

    public static boolean authenticateUser(String userId, String password) {
        if (hasUserId(userId)) {
            User user = findUserById(userId);
            return password.equals(user.getPassword());
        }
        return false;
    }

    public static boolean hasUserId(String userId) {
        return users.containsKey(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void clear() {
        users = Maps.newConcurrentMap();
    }
}
