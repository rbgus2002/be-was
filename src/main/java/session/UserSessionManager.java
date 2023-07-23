package session;

import common.http.Cookie;
import common.http.HttpRequest;
import common.http.HttpResponse;
import common.wrapper.Cookies;
import model.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserSessionManager {

    private static final String SESSION_COOKIE_KEY = "SID";
    private static final Map<String, User> sessionStorage = new ConcurrentHashMap<>();

    public static void createSession(User user, HttpResponse response, String pathRange) {
        String sessionId = UUID.randomUUID().toString();
        sessionStorage.put(sessionId, user);

        Cookie cookie = Cookie.of(SESSION_COOKIE_KEY, sessionId, pathRange);
        response.addCookie(cookie);
    }

    public static User getSession(HttpRequest request) {
        Cookies cookies = request.getCookies();
        String key = cookies.getValue(SESSION_COOKIE_KEY);

        if (key == null) {
            return null;
        }

        return sessionStorage.get(key);
    }

}
