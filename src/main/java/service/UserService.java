package service;

import db.Database;
import model.User;

public class UserService {
    public static void addUser(User user) {
        Database.addUser(user);
    }
}
