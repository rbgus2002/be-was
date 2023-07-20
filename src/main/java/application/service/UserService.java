package application.service;

import application.infrastructure.LocalUserRepository;
import application.infrastructure.UserRepository;
import application.model.User;
import application.service.dto.UserRequest;

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
}
