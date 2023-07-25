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

    private static final String SESSION_COOKIE_NAME = "SID";
    public static final Map<String, User> store = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    public static void createSession(User user, HTTPServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        store.put(sessionId, user);
        response.setHeader("Set-Cookie", SESSION_COOKIE_NAME + "=" + sessionId + "; Path=/");
        logger.debug("Cookie = {}", SESSION_COOKIE_NAME + "=" + sessionId);
    }

    public static User getSession(HTTPServletRequest request) {
        logger.debug("Cookie = {}", request.getHeader("Cookie"));
        String cookie = null;
        cookie = request.getHeader("Cookie");
        if (cookie == null) {
            throw new IllegalArgumentException("Cookie 정보가 없습니다.");
        }
        String[] tokens = cookie.split("sessionId=");

        for (String token : tokens) {
            String id = token.substring(0, token.indexOf(';') == token.length() ? token.length() - 1 : token.length());
            if (store.containsKey(id)) {
                return store.get(id);
            }
        }

        return null;
    }

}
