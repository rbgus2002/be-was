package webserver.session;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Session {
    private static final Logger logger = LoggerFactory.getLogger(Session.class);

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

    public User getUser() {
        return user;
    }

    public boolean isValid() {;
        return user != null;
    }
}
