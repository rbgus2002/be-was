package http;

public enum HttpStatus {
    OK(200),
    NO_CONTENT(203),
    FOUND(302),
    NOT_FOUND(404);

    private int value;

    HttpStatus(int httpStatus) {
        this.value = httpStatus;
    }

    public int value() {
        return this.value;
    }
}
