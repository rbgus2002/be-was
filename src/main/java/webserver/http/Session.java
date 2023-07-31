package webserver.http;

import com.google.common.collect.Maps;
import java.util.Map;

public enum Session {
    DEFAULT;

    public static final String SESSION_PREFIX = "sid=";

    private final Map<String, Object> storage = Maps.newConcurrentMap();

    public String put(final String key, final Object value) {
        storage.put(key, value);
        return key;
    }

    public Object get(final String key) {
        return storage.get(key);
    }
}
