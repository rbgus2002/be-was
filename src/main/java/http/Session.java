package http;

import com.google.common.collect.Maps;

import java.util.Map;

public class Session {
    private final Map<String, Object> attributes = Maps.newHashMap();
    private long lastAccessTime;
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30분


    private Session(String key, Object value) {
        attributes.put(key, value);
        this.lastAccessTime = System.currentTimeMillis();
    }

    public static Session of(String key, Object value) {
        return new Session(key, value);
    }

    public Object getValue(String key) {
        return attributes.get(key);
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void updateLastAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }

    /**
     * 테스트용 메소드
     */
    public void updateLastAccessTimeToZero(){
        this.lastAccessTime = 0;
    }

    public boolean validateExpiration() {
        return this.lastAccessTime + SESSION_TIMEOUT > System.currentTimeMillis();

    }

    @Override
    public String toString() {
        return "Session{" +
                "attributes=" + attributes +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }
}
