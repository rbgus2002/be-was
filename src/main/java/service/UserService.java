package service;

import model.User;

import java.util.Map;

import static db.Database.addUser;

public class UserService {
    private String USERID = "userId";
    private String PASSWORD = "password";
    private String NAME = "name";
    private String EMAIL = "email";
    public void createUser(Map<String, String> userInfo) {
        User user = new User(userInfo.get(USERID), userInfo.get(PASSWORD), userInfo.get(NAME), userInfo.get(EMAIL));
        addUser(user);
    }
}
