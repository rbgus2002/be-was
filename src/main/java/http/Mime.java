package http;

public enum Mime {

    HTML("html", "text/html"),
    JAVA_SCRIPT("js", "text/js"),
    CSS("css", "text/css"),
    ICO("ico", "image/x-icon"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpg"),
    DEFAULT("", "application/octet-stream");


    private final String extension;
    private final String type;

    Mime(String extension, String type) {
        this.extension = type;
        this.type = type;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getType() {
        return this.type;
    }
}