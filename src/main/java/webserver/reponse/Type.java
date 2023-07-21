package webserver.reponse;

public enum Type {
    CSS("text/css;charset=utf-8"), TEXT("text/plain;charset=utf-8"), HTML("text/html;charset=utf-8"), XML("application/xml"),
    JS("text/javascript;charset=utf-8"), JSON("application/jsoncharset=utf-8"), PNG("image/png"),
    EOT("application/vnd.ms-fontobject"), ICO("image/vnd.microsoft.icon"), SVG("image/svg+xml"),
    TTF("font/ttf"), WOFF("font/woff"), WOFF2("font/woff2");

    private String contentType;
    private Type(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public static Type getType(String extension) {
        switch (extension) {
            case "css":
                return CSS;
            case "xml":
                return XML;
            case "js":
                return JS;
            case "eot":
                return EOT;
            case "svg":
                return SVG;
            case "ttf":
                return TTF;
            case "woff":
                return WOFF;
            case "woff2":
                return WOFF2;
            case "png":
                return PNG;
            case "ico":
                return ICO;
            default:
                return null;
        }
    }
}
