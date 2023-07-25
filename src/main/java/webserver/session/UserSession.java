package webserver.session;

import model.User;
import webserver.reponse.HttpResponse;
import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

    public void putSession(User user, HttpResponse response) {
        String uuid = UUID.randomUUID().toString();
        userSessionMap.put(uuid, user);
        response.setHeader("Set-Cookie", "sid=" + uuid +"; Path=/");
    }


}
