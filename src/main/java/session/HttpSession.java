package session;

import java.time.LocalDateTime;
import java.util.List;

public class HttpSession {
    public static final long EXPIRE_HOUR = 60 * 60 * 1000;
    private List<Object> contents;
    private long lastAccessTime;

    public HttpSession(List<Object> contents) {
        this.contents = contents;
        lastAccessTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return lastAccessTime + EXPIRE_HOUR < System.currentTimeMillis();
    }
}
