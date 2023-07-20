package webserver.myframework.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionImpl implements Session{
    Map<String, Object> attributes = new ConcurrentHashMap<>();

    @Override
    public void setAttribute(String key, Object object) {
        attributes.put(key, object);
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }
}
