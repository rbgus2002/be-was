package webserver.response;

public enum HttpStatus {

    OK(200, "OK");

    private final int statusCode;
    private final String name;

    HttpStatus(int statusCode, String name) {
        this.statusCode = statusCode;
        this.name = name;
    }

    public String getDescription() {
        return 200 + " " + this.name;
    }
}
