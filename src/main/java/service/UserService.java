package service;

import db.UserRepository;
import exception.UserServiceException;
import model.User;

import java.util.Collection;
import java.util.Optional;

public class UserService {
    public static final String LOGIN_FAIL_EXCEPTION = "로그인 실패";
    public static final String INVALID_USERID_EXCEPTION = "그런 ID 없음";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        verifyNoDuplicateUserId(user);
        userRepository.save(user);
    }

    public User login(String loginId, String loginPassword) {
        User findUser = userRepository.findUserById(loginId)
                .orElseThrow(() -> new UserServiceException(INVALID_USERID_EXCEPTION));
        if (hasSameIdPassword(findUser,loginId, loginPassword)) {
            return findUser;
        }
        throw new UserServiceException(LOGIN_FAIL_EXCEPTION);
    }

    private boolean hasSameIdPassword(User user, String loginId, String loginPassword) {
        return user.getUserId().equals(loginId) && user.getPassword().equals(loginPassword);
    }

    private void verifyNoDuplicateUserId(User user) {
        Optional<User> optionalUser = userRepository.findUserById(user.getUserId());
        if (optionalUser.isPresent()) {
            throw UserServiceException.duplicatedId();
        }
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }
}