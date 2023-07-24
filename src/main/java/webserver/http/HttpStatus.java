package webserver.http;

public enum HttpStatus {
    OK("200", "OK"),
    FOUND("302", "Found");

    private final String statusCode;
    private final String status;

    HttpStatus(String statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }
}
