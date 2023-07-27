package session;

import model.HttpRequest;
import model.HttpResponse;
import model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static constant.Uri.USER_LIST_URI;

public class Authorization {
    private static final ConcurrentMap<String, HttpSession> session = new ConcurrentHashMap<>();

    public static Set<String> NEEDED_AUTHORIZATION = Set.of(
            USER_LIST_URI
    );
    
    private Authorization() {}

    public static Optional<HttpSession> getSession(HttpRequest request) {
        String sidInCookie = request.getSessionIdInCookie();
        return Optional.ofNullable(session.get(sidInCookie));
    }

    public static void setSessionInCookie(HttpResponse httpResponse, User user) {
        String sessionId = UUID.randomUUID().toString();
        httpResponse.setCookie(sessionId);
        addUserInSession(sessionId, user);
    }

    private static void addUserInSession(String sessionId, User target) {
        session.put(sessionId, new HttpSession(List.of(target)));
    }
}
