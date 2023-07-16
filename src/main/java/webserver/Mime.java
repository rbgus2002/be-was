package webserver;

public enum Mime {
    JPG(".jpg","image/jpeg" ), CSS(".css", "text/css"),
    ICO(".ico", "image/vnd.microsoft.icon"), TTF(".ttf", "font/ttf"),
    WOFF(".woff", "font/woff"), PNG(".png", "image/png"),
    JS(".js", "text/javascript"), HTML(".html", "text/html");

    private final String extension;
    private final String mimeType;

    Mime(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }
}
