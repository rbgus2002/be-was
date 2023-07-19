package db;

import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    public static final String DUPLICATE_PRIMARY_KEY = "Duplicate PK";
    public static final String NO_SUCH_PRIMARY_KEY = "No PK";
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public void save(User user) {
        Optional<User> findUser = findUserById(user.getUserId());
        if (findUser.isPresent()) throw new RuntimeException(DUPLICATE_PRIMARY_KEY);
        users.put(user.getUserId(), user);
    }

    public Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public void deleteUserById(String userId) {
        Optional<User> optionalUser = findUserById(userId);
        if (optionalUser.isEmpty()) throw new RuntimeException(NO_SUCH_PRIMARY_KEY);
        users.remove(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
