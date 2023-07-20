package service;

import db.Database;
import model.User;
import model.UserParam;

import java.util.Map;

public class UserService {
    public void register(Map<String, String> params) {
        final User user = new User(
                params.get(UserParam.ID),
                params.get(UserParam.PASSWORD),
                params.get(UserParam.NAME),
                params.get(UserParam.EMAIL));
        Database.addUser(user);
    }
}
