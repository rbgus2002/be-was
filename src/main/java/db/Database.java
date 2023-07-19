package db;

import com.google.common.collect.Maps;
import model.user.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Database {

    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void flush() {
        users = Maps.newHashMap();
    }
}
