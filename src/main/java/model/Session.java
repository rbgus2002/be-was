package model;

import java.util.UUID;

public class Session {

    private String sessionId;
    private User user;

    public Session() {
        sessionId = UUID.randomUUID().toString();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
