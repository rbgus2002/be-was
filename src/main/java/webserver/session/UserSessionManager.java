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

    public String putSession(User user, HttpResponse response) {
        String sessionId = UUID.randomUUID().toString();
        userSessionMap.put(sessionId, new UserSession(user, LocalDateTime.now().plusMinutes(30)));
        response.setHeader("Set-Cookie", SESSION_COOKIE_NAME + "=" + sessionId +"; Path=/");
        return sessionId;
    }

    public User getSession(HttpRequest request) {
        String sessionId = request.getSessionIdBySessionName(SESSION_COOKIE_NAME);

        if(sessionId == null) {
            return null;
        }

        UserSession userSession = userSessionMap.get(sessionId);
        if(userSession == null || userSession.isExpired()) {
            removeSession(request);
            return null;
        }

        return userSession.getUser();
    }

    public void expireSession(HttpRequest request) {
        if (getSession(request) != null) {
            removeSession(request);
        }
    }

    private void removeSession(HttpRequest request) {
        userSessionMap.remove(request.getSessionIdBySessionName(SESSION_COOKIE_NAME));
    }
}
