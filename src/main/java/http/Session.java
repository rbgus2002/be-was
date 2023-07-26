package http;

import com.google.common.collect.Maps;

import java.util.Map;

public class Session {
    private final Map<String, Object> attributes = Maps.newHashMap();
    private long lastAccessTime;

    // FIXME : sid 수정 고려
    private Session(String sid, Object obj) {
        attributes.put(sid, obj);
        this.lastAccessTime = System.currentTimeMillis();
    }

    public static Session of(String sid, Object obj) {
        return new Session(sid, obj);
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void updateLastAccessTime(){
        this.lastAccessTime = System.currentTimeMillis();
    }
}
