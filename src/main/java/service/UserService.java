package service;

import model.User;

import java.util.Map;

import static db.Database.*;
import static exception.ExceptionName.ALREADY_EXIST_USER;

public class UserService {
    private String USERID = "userId";
    private String PASSWORD = "password";
    private String NAME = "name";
    private String EMAIL = "email";
    public void createUser(Map<String, String> userInfo) {
        checkUserAlreadyExist(userInfo.get(USERID));
        User user = new User(userInfo.get(USERID), userInfo.get(PASSWORD), userInfo.get(NAME), userInfo.get(EMAIL));
        addUser(user);
    }

    private void checkUserAlreadyExist(String userId) {
        if (findAll().stream().anyMatch(user -> user.getUserId().equals(userId)))
            throw new IllegalArgumentException(ALREADY_EXIST_USER);
    }
}
