package config;

import db.UserRepository;
import service.UserService;

public class UserConfig {
    private UserConfig() {
    }

    private static final UserRepository userRepository = new UserRepository();
    private static final UserService userService = new UserService(userRepository);

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static UserService getUserService() {
        return userService;
    }
}
