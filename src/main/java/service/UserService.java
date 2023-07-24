package service;

import exception.BadRequestException;
import model.User;

import java.util.Map;

import static db.Database.*;
import static db.SessionDatabase.*;
import static exception.ExceptionList.ALREADY_EXIST_USER;
import static exception.ExceptionList.NOT_EXIST_USER;

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
            throw new BadRequestException(ALREADY_EXIST_USER);
    }

    public String loginUser(Map<String, String> userInfo) {
        checkUserExist(userInfo);
        String sessionId = createSessionId();
        addSessionId(userInfo.get(USERID), sessionId);
        return sessionId;
    }

    private void checkUserExist(Map<String, String> userInfo) {
        if (findAll().stream().noneMatch(user -> user.getUserId().equals(userInfo.get(USERID)) &&
                                                 user.getPassword().equals(userInfo.get(PASSWORD))))
            throw new BadRequestException(NOT_EXIST_USER);
    }

    private boolean checkSessionIdExist(String sessionId) {
        return findAllSessionIds().stream().anyMatch(id -> id.equals(sessionId));
    }
}
