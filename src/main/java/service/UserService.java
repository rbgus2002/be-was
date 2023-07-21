package service;

import db.UserRepository;
import model.User;

import java.util.Optional;

public class UserService {
    public static final String DUPLICATED_ID = "이미 등록된 userId 입니다";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        verifyNoDuplicateUserId(user);
        userRepository.save(user);
    }

    private void verifyNoDuplicateUserId(User user) {
        Optional<User> optionalUser = userRepository.findUserById(user.getUserId());
        if (optionalUser.isPresent()) {
            throw new RuntimeException(DUPLICATED_ID);
        }
    }
}