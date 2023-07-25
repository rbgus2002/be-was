package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.*;
import java.util.stream.Collectors;

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
