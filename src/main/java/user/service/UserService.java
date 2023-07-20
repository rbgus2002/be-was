package user.service;

import db.Database;
import model.User;
import webserver.myframework.requesthandler.annotation.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class UserService {

    public void signUp(String userId, String password, String name, String email) {
        User findUser = Database.findUserById(userId);
        if (findUser != null) {
            throw new IllegalArgumentException();
        }

        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }

    public User signIn(String userId, String password) {
        User user = Database.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }
}
