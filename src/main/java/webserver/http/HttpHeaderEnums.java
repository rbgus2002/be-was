package webserver.http;

public enum HttpHeaderEnums {
    METHOD("Method"), URI("URI"), VERSION("Version"), CRLF("\r\n");

    private String value;

    private HttpHeaderEnums(String value) {
        this.value = value;
    }

    public String getString() {
        return value;
    }
}
