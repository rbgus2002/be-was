package http;

public enum MIME {

    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "text/javascript"),
    ICO("ico", "image/vnd.microsoft.icon"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    WOFF("woff", "font/woff");

    private final String extension;
    private final String contentType;

    MIME(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public String getExtension() {
        return this.extension;
    }

    public String getContentType() {
        return this.contentType;
    }
}
