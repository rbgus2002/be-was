package session;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.Cookie;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//TODO: 세션 매니저 유저에 종속적이지 않게 만들기
public class UserSessionManager {
    private static final Logger log = LoggerFactory.getLogger(UserSessionManager.class);
    private static final Map<String, User> userSession = new ConcurrentHashMap<>();

    public static Cookie addUser(User user) {
        String sessionId = createSessionId();
        Cookie cookie = Cookie.create(sessionId);
        userSession.put(sessionId, user);
        log.info("=====세션 생성=====");
        log.info("세션 id = {}", sessionId);
        log.info("쿠키 만료 시간 = {}", cookie.getExpires());
        return cookie;
    }

    private static String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public static User getUser(String sessionId) {
        return userSession.get(sessionId);
    }
}
