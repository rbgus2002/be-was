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


    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public static List<UserResponseDto> findAllUser() {
        return users.values()
                .stream()
                .map(UserResponseDto::of)
                .collect(Collectors.toList());
    }
}
