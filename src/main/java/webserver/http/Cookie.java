package webserver.http;

import java.util.Map;

import static service.SessionService.isValidSession;

public class Cookie {
    private Map<String, String> attributes;

    public Cookie(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getSessionId() {
        return attributes.getOrDefault("sid", null);
    }

    public static boolean isValidCookie(Cookie cookie) {
        if (cookie == null)
            return false;

        return isValidSession(cookie.getSessionId());
    }
}
