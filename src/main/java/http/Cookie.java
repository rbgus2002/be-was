package http;

import java.time.LocalDateTime;

public class Cookie {
    public static final String SESSIONID = "SID";
    private final String name;
    private final String value;
    private final LocalDateTime expires;

    private Cookie(String value) {
        this.name = SESSIONID;
        this.value = value;
        this.expires = LocalDateTime.now().plusMinutes(10);
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

    public static Cookie create(String value) {
        return new Cookie(value);
    }
}
