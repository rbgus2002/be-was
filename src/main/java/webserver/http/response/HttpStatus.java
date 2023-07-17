package webserver.http.response;

public enum HttpStatus {
    OK(200),
    FOUND(302),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    METHOD_NOT_ALLOW(405),
    INTERNAL_SERVER_ERROR(500);

    private final int statusNumber;
    HttpStatus(int statusNumber) {
        this.statusNumber = statusNumber;
    }

    public int getStatusNumber() {
        return statusNumber;
    }
}
