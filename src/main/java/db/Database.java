package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    // 테스트용 데이터
    static {
        users.put("qq", new User("qq","qq","kim","qq@qq.qq"));
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
