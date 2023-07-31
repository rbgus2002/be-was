package webserver.http;

public class Cookie {
    protected static final String SET_HEADER_KEY = "Set-Cookie";

    private final String value;

    public Cookie(final String value) {
        this.value = Session.SESSION_PREFIX + value + "; Path=/;";
    }

    public String getValue() {
        return value;
    }
}
