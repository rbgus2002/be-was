package webserver.response;

public enum HttpStatus {

    OK(200, "200 OK"),
    FOUND(302, "302 Found"),
    BAD_REQUEST(400, "400 Bad Request"),
    NOT_FOUND(404, "404 Not Found"),
    METHOD_NOT_ALLOWED(405, "405 Method Not Allowed");

    private final int status;
    private final String message;

    HttpStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
