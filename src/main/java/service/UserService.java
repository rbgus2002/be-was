package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void registerUser(Map<String, String> body) {
        User user = new User(
                body.get("userId"),
                body.get("password"),
                body.get("name"),
                body.get("email")
        );
        Database.addUser(user);
        logger.debug("User: {}", user);
    }

    public boolean existUser(String userId, String password) {
        User user = Database.findUserById(userId);

        if (user != null && user.getPassword().equals(password)) {
            return true;
        }

        return false;
    }
}
