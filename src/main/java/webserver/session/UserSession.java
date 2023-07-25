package webserver.session;

import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserSession {

    private static UserSession userSession;

    private final ConcurrentHashMap<String, User> userSessionMap;

    private UserSession() {
        userSessionMap = new ConcurrentHashMap<>();
    }

    public static UserSession getInstance() {
        if(userSession == null) {
            userSession = new UserSession();
        }
        return userSession;
    }

    public void putSession() {

    }
}
