package model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Session {

    private final Map<String, Object> attributes = new ConcurrentHashMap<>();
    private long lastAccessedTime;

    public Session() {
        this.lastAccessedTime = System.currentTimeMillis();
    }

    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    public void setLastAccessedTimeNow() {
        this.lastAccessedTime = System.currentTimeMillis();
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }
}