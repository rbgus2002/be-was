package webserver;

import java.util.Arrays;

public enum Mime {
    JPG(".jpg", "image/jpeg"), CSS(".css", "text/css"),
    ICO(".ico", "image/vnd.microsoft.icon"), TTF(".ttf", "font/ttf"),
    WOFF(".woff", "font/woff"), PNG(".png", "image/png"),
    JS(".js", "text/javascript"), HTML(".html", "text/html");

    private final String extension;
    private final String mimeType;

    Mime(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public static Mime findByExtension(String extension) {
        return Arrays.stream(Mime.values())
                .filter(mime -> mime.extension.equals(extension))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(extension + "은 지원하는 확장자가 아닙니다."));
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getExtension() {
        return extension;
    }
}
