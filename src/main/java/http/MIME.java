package http;

public enum MIME {

    html("html", "text/html"),
    css("css", "text/css"),
    js("js", "text/javascript"),
    ico("ico", "image/vnd.microsoft.icon"),
    png("png", "image/png"),
    jpg("jpg", "image/jpeg"),
    woff("woff", "font/woff");

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
