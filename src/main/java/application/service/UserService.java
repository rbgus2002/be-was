package application.service;

import application.infrastructure.LocalUserRepository;
import application.infrastructure.UserRepository;
import application.model.User;
import application.service.dto.LoginRequest;
import application.service.dto.UserRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService() {
        this.userRepository = new LocalUserRepository();
        this.userMapper = new UserMapper();
    }

    public void create(final UserRequest userRequest) {
        User user = userMapper.userFrom(userRequest);
        this.userRepository.save(user);
    }

    public boolean login(final LoginRequest loginRequest) {
        Optional<User> findUser = userRepository.findUserById(loginRequest.getUserId());
        if (findUser.isEmpty()) {
            return false;
        }
        return findUser.get().getPassword().equals(loginRequest.getPassword());
    }

    public List<User> getAll() {
        return new ArrayList<>(userRepository.findAll());
    }
}
