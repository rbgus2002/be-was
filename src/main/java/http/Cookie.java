package http;

import java.time.LocalDateTime;

public class Cookie {
    public static final String SESSIONID = "SID";
    private final String name;
    private final String value;
    private final LocalDateTime expires;

    private Cookie(String value, LocalDateTime expires) {
        this.name = SESSIONID;
        this.value = value;
        this.expires = expires;
    }

    public String getName() {
        return name;
    }
    public String getValue() {
        return value;
    }
    public LocalDateTime getExpires() {
        return expires;
    }

    public static Cookie create(String value, LocalDateTime expires) {
        return new Cookie(value, expires);
    }

}
