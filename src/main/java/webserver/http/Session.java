package webserver.http;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

public class Session {
    private static final Map<String, Object> sessionStorage = Maps.newConcurrentMap();

    public static String addSession(Object value) {
        String key = UUID.randomUUID().toString();
        sessionStorage.put(key, value);
        return key;
    }

    public static Object get(String key) {
        if (key == null) {
            return null;
        }
        return sessionStorage.get(key);
    }

    public static void removeSession(String sid) {
        sessionStorage.remove(sid);
    }
}
