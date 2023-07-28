package session;

import java.util.UUID;

public class Session {

    private final String sessionId;

    private Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public static Session create() {
        return new Session(UUID.randomUUID().toString());
    }

    public String getSessionId() {
        return sessionId;
    }

    public boolean verify(String sessionId) {
        return this.sessionId.equals(sessionId);
    }
}
