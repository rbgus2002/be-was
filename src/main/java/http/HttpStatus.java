package http;

public enum HttpStatus {
    OK(200, "OK"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");


    private int value;
    private String reasonPhrase;

    HttpStatus(int httpStatus, String reasonPhrase) {
        this.value = httpStatus;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String reasonPhrase() {
        return reasonPhrase;
    }
}
