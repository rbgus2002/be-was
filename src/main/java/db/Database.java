package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {
    public static final String USERID_ALREADY_EXISTS_MESSAGE = "이미 userId가 존재합니다.";
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        CheckDuplicateUserId(user.getUserId());
        users.put(user.getUserId(), user);
    }

    private static void CheckDuplicateUserId(final String userId) {
        if (findUserById(userId) != null) {
            throw new IllegalArgumentException(USERID_ALREADY_EXISTS_MESSAGE);
        }
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
