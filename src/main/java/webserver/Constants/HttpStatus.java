package webserver.Constants;

import static support.utils.StringUtils.*;

public enum HttpStatus {

    OK(200, "OK"),
    CREATED(201, "CREATED"),
    SEE_OTHER(303, "See Other"),
    BAD_REQUEST(400, "BAD REQUEST"),
    NOT_FOUND(404, "NOT FOUND"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported")
    ;

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
