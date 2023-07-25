package webserver.session;

import model.User;
import webserver.reponse.HttpResponse;
import webserver.request.HttpRequest;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserSessionManager {

    private static UserSessionManager userSessionManager;
    private final String SESSION_COOKIE_NAME = "sid";
    private final ConcurrentHashMap<String, UserSession> userSessionMap;

    private UserSessionManager() {
        userSessionMap = new ConcurrentHashMap<>();
    }

    public static UserSessionManager getInstance() {
        if(userSessionManager == null) {
            userSessionManager = new UserSessionManager();
        }
        return userSessionManager;
    }

    public void putSession(User user, HttpResponse response) {
        String uuid = UUID.randomUUID().toString();
        userSessionMap.put(uuid, new UserSession(user, LocalDateTime.now().plusMinutes(30)));
        response.setHeader("Set-Cookie", SESSION_COOKIE_NAME + "=" + uuid +"; Path=/");
    }

    public User getSession(HttpRequest request) {
        UserSession userSession = userSessionMap.get(request.getSessionIdBySessionName(SESSION_COOKIE_NAME));
        if(userSession == null || userSession.isExpired()) {
            expireSession(request);
            return null;
        }

        return userSession.getUser();
    }

    public void expireSession(HttpRequest request) {
        if (getSession(request) != null) {
            userSessionMap.remove(request.getSessionIdBySessionName(SESSION_COOKIE_NAME));
        }
    }
}
