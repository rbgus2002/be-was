package model;

import java.util.UUID;

public class Session {
    private final String userId;
    private String sessionId;

    public Session(String userId) {
        this.userId = userId;
        this.sessionId = UUID.randomUUID().toString();
    }

    public String getUserId() {
        return userId;
    }
    public String getSessionId() {
        return sessionId;
    }
    public String setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this.sessionId;
    }
}
