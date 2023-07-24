package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.Collection;
import java.util.Map;

public enum Database {

    INSTANCE;

    private final Map<String, User> users = Maps.newConcurrentMap();

    public void addUser(final User user) {
        users.put(user.getUserId(), user);
    }

    public User findUserById(final String userId) {
        return users.get(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
