package webserver.Constants;

public enum HttpStatus {

    OK(200, "OK"),
    NOT_FOUND(404, "NOT FOUND"),
    CREATED(201, "CREATED");

    private final int statusCode;
    private final String name;

    HttpStatus(int statusCode, String name) {
        this.statusCode = statusCode;
        this.name = name;
    }

    public String getDescription() {
        return statusCode + " " + this.name;
    }
}
