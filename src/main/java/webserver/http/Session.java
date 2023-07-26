package webserver.http;

import com.google.common.collect.Maps;

import java.util.Map;

public class Session {
    private static final Map<String, Object> sessionStorage = Maps.newConcurrentMap();

    public static void put(String key, Object value) {
        sessionStorage.put(key, value);
    }

    public static Object get(String key) {
        return sessionStorage.get(key);
    }
}
