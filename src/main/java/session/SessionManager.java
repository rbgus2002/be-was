package session;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//TODO: 세션 매니저 유저에 종속적이지 않게 만들기
public class SessionManager {
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private SessionManager() {}

    public static String addSession(Object object) {
        String sessionId = createSessionId();
        Session session = new Session(object);
        sessions.put(sessionId, session);
        log.info("=====세션 생성=====");
        log.info("세션 id = {}", sessionId);
        log.info("세션 만료 시간 = {}", session.getExpires());
        return sessionId;
    }

    private static String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public static Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static User getUser(String sessionId) {
        Session session = sessions.get(sessionId);
        return (User) session.getObject();
    }
}
