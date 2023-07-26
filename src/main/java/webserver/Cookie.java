package webserver;

import java.time.LocalDateTime;

public class Cookie {

    private static final String SESSION_ID = "sid";
    private final String name;
    private final String value;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static Cookie createCookie(String value) {
        return new Cookie(SESSION_ID, value);
    }
}
