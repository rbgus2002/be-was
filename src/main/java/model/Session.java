package model;

import java.util.UUID;

public class Session {
    private final User user;
    private String sessionId;

    public Session(User user) {
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
