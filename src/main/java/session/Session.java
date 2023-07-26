package session;

import java.time.Duration;
import java.time.Instant;

public class Session {
    private final String userId;
    private final Instant expired;

    public Session(String userId) {
        this.userId = userId;
        int PERIOD_HOURS = 1;
        this.expired = Instant.now().plus(Duration.ofHours(PERIOD_HOURS));
    }

    public String getUserId() {
        return userId;
    }

    public boolean isExpired() {
        Instant now = Instant.now();
        Duration duration = Duration.between(expired, now);
        return !duration.isNegative();
    }
}
