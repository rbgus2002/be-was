package global.constant;

public enum Headers {
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    CONTENT_TYPE("Content-Type");

    private final String key;

    Headers(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}