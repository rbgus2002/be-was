package service;

import db.Database;
import model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserService {

    private UserService() {}

    public static User createUser(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        Database.addUser(user);
        return user;
    }

    public static Optional<User> login(String userId, String password) {
        return Database.findUserById(userId)
                       .filter(user -> user.identify(password));
    }

    public static List<User> getAllUsers() {
        return Database.findAll();
    }
}
