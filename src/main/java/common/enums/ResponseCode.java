package common.enums;

public enum ResponseCode {
    OK("HTTP/1.1 200 OK"),
    FOUND("HTTP/1.1 302 Found"),
    BAD_REQUEST("HTTP/1.1 400 Bad Request"),
    FORBIDDEN("HTTP/1.1 403 Forbidden"),
    NOT_FOUND("HTTP/1.1 404 Not Found");

    private final String description;

    ResponseCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
