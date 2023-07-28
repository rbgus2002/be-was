package service;

import domain.User;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserService() {}

    public static User createUser(String userId, String password, String name, String email) {
        validateUserParameters(userId, password, name, email);

        User user = new User(userId, password, name, email);
        UserRepository.addUser(user);
        return user;
    }

    public static Optional<User> login(String userId, String password) {
        validateUserParameters(userId, password);

        return UserRepository.findUserById(userId)
                       .filter(user -> user.identify(password));
    }

    public static List<User> getAllUsers() {
        return UserRepository.findAll();
    }

    private static void validateUserParameters(String... parameters) {
        for (String parameter : parameters) {
            if (parameter == null || parameter.isEmpty()) {
                throw new IllegalArgumentException("사용자 정보가 비어있거나 없습니다.");
            }
        }
    }
}
