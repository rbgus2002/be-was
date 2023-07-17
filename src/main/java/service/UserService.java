package service;

import db.Database;
import model.User;

public class UserService {

    private UserService() {
    }

    public static User createUser(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        Database.addUser(user);
        return user;
    }
}
