package domain.user;

import com.google.common.collect.Maps;

import domain.user.User;

import java.util.*;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static List<User> findAll() {
        return new ArrayList<>(users.values());
    }

}
