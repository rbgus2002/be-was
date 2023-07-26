package global.constant;

public enum Headers {
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    CONTENT_TYPE("Content-Type"),
    CHARSET_UTF8("charset=utf-8"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie"),
    SESSION_ID("sid");

    private final String key;

    Headers(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}