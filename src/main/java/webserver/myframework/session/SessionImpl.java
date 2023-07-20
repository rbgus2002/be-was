package webserver.myframework.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionImpl implements Session {
    private final String sessionId;
    Map<String, Object> attributes = new ConcurrentHashMap<>();

    public SessionImpl(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setAttribute(String key, Object object) {
        attributes.put(key, object);
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }
}
