package http;

public enum ContentType {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/javascript"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpeg"),
    EOT("application/vnd.ms-fontobject"),
    SVG("image/svg+xml"),
    TTF("application/x-font-ttf"), // OR font/ttf
    WOFF("application/font-woff"), // OR font/woff
    WOFF2("font/woff2");

    private String mimeType;

    ContentType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}
