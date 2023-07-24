package webserver.session;

import model.User;

public class Session {
    private final String sessionId;
    private User user;

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getId() {
        return sessionId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValid() {
        return user != null;
    }
}
