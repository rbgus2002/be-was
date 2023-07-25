package webserver.http.message;

import java.util.List;

public class Cookie {
    public static final String SID = "sid";
    public static final String EQUAL = "=";
    public final List<String> cookies;

    public Cookie(List<String> cookies) {
        this.cookies = cookies;
    }

    public static Cookie from(List<String> cookie) {
        return new Cookie(cookie);
    }

    public String getSessionValue() {
        for (String cookie : cookies) {
            if (cookie.startsWith(SID)) {
                return cookie.split(EQUAL)[1];
            }
        }
        return null;
    }
}
