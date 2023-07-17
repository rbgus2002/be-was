package http;

public enum Mime {

    HTML("text/html"),
    JAVA_SCRIPT("text/js"),
    CSS("text/css"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpg"),
    DEFAULT("application/octet-stream");

    private final String type;

    Mime(String type) {
        this.type = type;
    }
}