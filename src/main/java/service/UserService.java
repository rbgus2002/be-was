package service;

import exception.BadRequestException;
import exception.NotExistUserException;
import exception.SessionIdException;
import model.User;

import java.util.Map;

import static db.Database.*;
import static db.SessionDatabase.*;
import static exception.ExceptionList.*;

public class UserService {
    private final String USERID = "userId";
    private final String PASSWORD = "password";
    private final String NAME = "name";
    private final String EMAIL = "email";

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
        addSessionId(sessionId, userInfo.get(USERID));
        return sessionId;
    }

    private void checkUserExist(Map<String, String> userInfo) {
        if (findAll().stream().noneMatch(user -> user.getUserId().equals(userInfo.get(USERID)) &&
                user.getPassword().equals(userInfo.get(PASSWORD))))
            throw new NotExistUserException(NOT_EXIST_USER);
    }

    public void logoutUser(String sessionId) {
        checkSessionIdExist(sessionId);
        deleteSessionId(sessionId);
    }

    private void checkSessionIdExist(String sessionId) {
        if (findAllSessionIds().stream().noneMatch(id -> id.equals(sessionId)))
            throw new SessionIdException(NOT_EXIST_SESSION_ID);
    }
}
