package session;

import java.time.LocalDateTime;

public class Session {
    private final Object object;
    private final LocalDateTime expires;

    public Session(Object object) {
        this.object = object;
        this.expires = LocalDateTime.now().plusMinutes(20);
    }

    public Object getObject() {
        return object;
    }

    public LocalDateTime getExpires() {
        return expires;
    }
}
