package model;

import java.util.UUID;

public class UserSession {
    private final User user;
    private String sessionId;

    public UserSession(User user) {
        this.user = user;
        this.sessionId = UUID.randomUUID().toString();
    }

    public User getUser() {
        return user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String regenerateSessionId() {
        sessionId = UUID.randomUUID().toString();
        return sessionId;
    }
}
