package webserver.http;

public class Cookie {
    public static final String HEADER_KEY = "Cookie";
    protected static final String SET_HEADER_KEY = "Set-Cookie";
    public static final String COOKIE_PREFIX = "sid=";

    private final String value;

    public Cookie(final String value) {
        this.value = COOKIE_PREFIX + value + "; Path=/;";
    }

    public String getValue() {
        return value;
    }
}
