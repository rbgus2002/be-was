package model;

public class Session {
    private String sessionId;
    private User user;

    public Session(String sessionId, User user) {
        this.sessionId = sessionId;
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public User getUser() {
        return user;
    }
}
