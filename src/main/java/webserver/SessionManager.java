package webserver;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<String, Object> sessionStorage = new ConcurrentHashMap<>();

    public void createSession(Object object) {
        String sessionId = createSessionId();
        sessionStorage.put(sessionId, object);
    }

    private String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public String getSessionId(Object object) {
        for (String key : sessionStorage.keySet()) {
            Object value = sessionStorage.get(key);
            if (object.equals(value)) {
                return key;
            }
        }

        throw new IllegalArgumentException("존재하지 않습니다.");
    }

    public Object getObject(String sessionId) {
        return sessionStorage.get(sessionId);
    }
}
