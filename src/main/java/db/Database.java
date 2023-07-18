package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static final Map<String, User> users = Maps.newHashMap();

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
}
