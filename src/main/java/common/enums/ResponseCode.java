package common.enums;

public enum ResponseCode {
    OK("200 OK"),
    CREATED("201 Created"),
    ACCEPTED("202 Accepted"),
    BAD_REQUEST("400 Bad Request"),
    FORBIDDEN("403 Forbidden"),
    NOT_FOUND("404 Not Found");

    private final String description;

    ResponseCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
