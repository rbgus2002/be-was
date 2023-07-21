package user.service;

import db.Database;
import model.User;
import webserver.myframework.requesthandler.annotation.Service;

@Service
public class UserService {
    public void signUp(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }
}
