package webserver.session;

import model.User;

import java.time.LocalDateTime;

public class UserSession {
    private User user;
    private LocalDateTime expiredTime;

    public UserSession(User user, LocalDateTime expiredTime) {
        this.user = user;
        this.expiredTime = expiredTime;
    }

    public User getUser() {
        return user;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredTime);
    }

}