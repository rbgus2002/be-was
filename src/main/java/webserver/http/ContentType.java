package webserver.http;

public enum ContentType {
    CSS("text/css"),
    HTML("text/html"),
    JAVA_SCRIPT("text/javascript"),
    ICO("image/x-icon"),
    PNG("image/x-png"),
    JPG("image/jpeg"),
    TTF("application/x-font-ttf"),
    WOFF("application/font-woff");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ContentType getContentType(String extension) {
        switch (extension) {
            case "css" :
                return CSS;
            case "html" :
                return HTML;
            case "js" :
                return JAVA_SCRIPT;
            case "ico" :
                return ICO;
            case "png" :
                return PNG;
            case "jpg" :
                return JPG;
            case "ttf" :
                return TTF;
            case "woff" :
                return WOFF;
            default:
                throw new IllegalArgumentException("해당 파일은 지원하지 않습니다");
        }
    }
}
