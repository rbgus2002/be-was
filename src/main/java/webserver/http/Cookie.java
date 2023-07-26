package webserver.http;

import java.time.LocalDateTime;

public class Cookie {
    public static final String SESSIONID = "SID";
    private final String name;
    private final String value;
    private LocalDateTime expires;

    private Cookie(String name, String value) {
        this.name = name;
        this.value = value;
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

    public void setExpires() {
        expires = LocalDateTime.now().plusMinutes(10);
    }

    public static Cookie create(String value) {
        return new Cookie(SESSIONID, value);
    }
}
