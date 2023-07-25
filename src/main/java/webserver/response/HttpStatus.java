package webserver.response;

public enum HttpStatus {
    OK("200", "OK"),
    CREATED("201", "Created"),
    FOUND("302", "Found"),
    BAD_REQUEST("400", "Bad Request"),
    FORBIDDEN("403", "Forbidden"),
    NOT_FOUND("404", "Not Found"),
    CONFLICT("409", "Conflict"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error");

    private final String code;
    private final String message;

    HttpStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static HttpStatus fromCode(String code) {
        for (HttpStatus status : HttpStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching HttpStatus for code: " + code);
    }
}
