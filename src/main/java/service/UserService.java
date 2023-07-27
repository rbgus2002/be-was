package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.SessionManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final SessionManager sessionManager = new SessionManager();

    public void registerUser(Map<String, String> body) throws UnsupportedEncodingException {
        User user = new User(
                URLDecoder.decode(body.get("userId"), StandardCharsets.UTF_8),
                URLDecoder.decode(body.get("password"), StandardCharsets.UTF_8),
                URLDecoder.decode(body.get("name"), StandardCharsets.UTF_8),
                URLDecoder.decode(body.get("email"), StandardCharsets.UTF_8)
        );
        Database.addUser(user);
        logger.debug("User: {}", user);
    }

    public boolean existUser(String userId, String password) {
        User user = Database.findUserById(userId);

        return user != null && user.getPassword().equals(password);
    }

    public String signIn(String userId) {
        User user = Database.findUserById(userId);
        sessionManager.createSession(user);
        return sessionManager.getSessionId(user);
    }

    public List<User> findAll() {
        return new ArrayList<>(Database.findAll());
    }
}
