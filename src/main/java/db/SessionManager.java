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
        String cookie;
        cookie = request.getHeader("Cookie");
        if (cookie == null) {
            return null;
        }
        String[] tokens = cookie.split("SID=");
        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }
            String id = token.trim();
            logger.debug("id는 = {}", id);
            if (store.containsKey(id)) {
                return store.get(id);
            }
        }

        return null;
    }

    public static void invalidateSession(String sid) {
        store.remove(sid);
    }

}
