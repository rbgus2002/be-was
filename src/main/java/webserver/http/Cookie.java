package webserver.http;

import java.time.LocalDateTime;

public class Cookie {
    private final String name;
    private final String value;
    private LocalDateTime expires;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setExpires() {
        expires = LocalDateTime.now().plusMinutes(10);
    }
}
