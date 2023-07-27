package db;

import com.google.common.collect.Maps;
import dto.UserResponseDto;
import model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    // 테스트용 데이터
    static {
        users.put("qq", new User("qq", "qq", "kim", "qq@qq.qq"));
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static List<UserResponseDto> findAll() {
        return users.values()
                .stream()
                .map(UserResponseDto::of)
                .collect(Collectors.toList());
    }
}
