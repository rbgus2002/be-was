package webserver.http;

public enum HttpStatusCode {
    OK(200),
    NOT_FOUND(404);

    private int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code + " " + this.name();
    }
}
