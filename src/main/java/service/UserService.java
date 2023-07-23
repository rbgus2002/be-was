package service;

import db.UserRepository;
import exception.UserServiceException;
import model.User;

import java.util.Optional;

public class UserService {
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
            throw UserServiceException.duplicateId();
        }
    }
}