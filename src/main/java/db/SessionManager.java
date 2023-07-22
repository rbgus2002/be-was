package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static final long SESSION_TIMEOUT_MS = 30 * 60 * 1000; // 30분 (단위: 밀리초)

    public static String createSession() {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new Session());
        Session session = sessions.get(sessionId);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (session.getLastAccessedTime() + SESSION_TIMEOUT_MS <= System.currentTimeMillis()) {
                    sessions.remove(sessionId);
                    timer.cancel();
                }
            }
        }, SESSION_TIMEOUT_MS, SESSION_TIMEOUT_MS);

        logger.debug("세션을 생성했습니다. sid : {}", sessionId);
        return sessionId;
    }

    public static Session getSession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session == null) {
            return null;
        }
        session.setLastAccessedTimeNow();
        return session;
    }

    public static void invalidateSession(String sessionId) {
        logger.debug("세션을 제거했습니다. sid : {}", sessionId);
        sessions.remove(sessionId);
    }
}
