package webserver.Constants;

import static support.utils.StringUtils.*;

public enum HttpStatus {

    OK(200, "OK"),
    NOT_FOUND(404, "NOT FOUND"),
    CREATED(201, "CREATED"),
    BAD_REQUEST(400, "BAD REQUEST");

    private final int statusCode;
    private final String name;

    HttpStatus(int statusCode, String name) {
        this.statusCode = statusCode;
        this.name = name;
    }

    public String getDescription() {
        return statusCode + SPACE + this.name;
    }
}
