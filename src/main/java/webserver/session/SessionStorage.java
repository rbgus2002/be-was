package webserver.session;

import com.google.common.collect.Maps;

import java.util.Map;

public class SessionStorage {
    private static Map<String, String> sessionMap = Maps.newHashMap();

    public static void setSession(String key, String value) {
        sessionMap.put(key, value);
    }
}
