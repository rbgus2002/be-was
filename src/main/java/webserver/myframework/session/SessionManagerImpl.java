package webserver.myframework.session;

import webserver.myframework.bean.annotation.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManagerImpl implements SessionManager {
    private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @Override
    public Session createSession(String sessionId) {
        Session session = new SessionImpl();
        sessionMap.put(sessionId, session);
        return session;
    }

    @Override
    public Session findSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionMap.remove(sessionId);
    }
}
