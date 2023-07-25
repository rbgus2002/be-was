package http;

public enum HttpStatus {
    OK(200),
    NOT_FOUND(404),
    BAD_REQUEST(400),
    FOUND(303),
    ;

    private final int code;

    HttpStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
