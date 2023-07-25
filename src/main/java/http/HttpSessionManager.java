package http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionManager {
    private static final Map<String, HttpSession> sessionMap = new ConcurrentHashMap<>();

    private HttpSessionManager() {}


    public static HttpSession getSession(String sid) {
        return sessionMap.get(sid);
    }

    public static void setSession(String sid, HttpSession session) {
        sessionMap.put(sid, session);
    }

    public static boolean containsSid(String sid) {
        return sessionMap.containsKey(sid);
    }
}
