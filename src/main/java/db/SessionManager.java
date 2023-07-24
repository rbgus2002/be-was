package db;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final String SESSION_COOKIE_NAME = "sessionId";
    public static final Map<String, User> store = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    public static void createSession(User user, HTTPServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        store.put(sessionId, user);
        response.setHeader("Set-Cookie", SESSION_COOKIE_NAME + "=" + sessionId  + "; Path=/");
        logger.debug("Cookie = {}", SESSION_COOKIE_NAME + "=" + sessionId);
    }

    public static User getSession(HTTPServletRequest request) {
        String sessionId = request.getHeader("Cookie").split("=")[1].trim();
        logger.debug("sessionId = {}", sessionId);
        if (sessionId == null) {
            throw new IllegalArgumentException("없는 쿠키입니다.");
        }
        return store.get(sessionId);
    }

}
