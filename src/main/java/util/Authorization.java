package util;

import model.HttpResponse;
import model.User;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static constant.Uri.USER_LIST_URI;

public class Authorization {
    private static final ConcurrentMap<String, User> session = new ConcurrentHashMap<>();

    public static Set<String> needAuthorization = Set.of(
            USER_LIST_URI
    );

    public static void setSessionInCookie(HttpResponse httpResponse, User user) {
        String sessionId = UUID.randomUUID().toString();
        httpResponse.setCookie(sessionId);
        addUserInSession(sessionId, user);
    }

    private static void addUserInSession(String sessionId, User target) {
        session.put(sessionId, target);
    }
}
