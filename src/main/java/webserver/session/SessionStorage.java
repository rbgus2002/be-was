package webserver.session;

import com.google.common.collect.Maps;

import java.util.Map;

public class SessionStorage {
    private static Map<String, String> sessionMap = Maps.newConcurrentMap();

    public static void setSession(String key, String value) {
        sessionMap.put(key, value);
    }

    public static void deleteSession(String key) {
        sessionMap.remove(key);
    }

    public static String getSession(String key) {
        return sessionMap.get(key);
    }

    public static void clear() {
        sessionMap = Maps.newConcurrentMap();
    }

    public static boolean hasSession(String key) {
        return sessionMap.containsKey(key);
    }
}
