package webserver.http.constant;

import java.util.Arrays;

public enum HttpMIME {
    CSS(".css", "text/css"),
    HTML(".html", "text/html"),
    ICO(".ico", "image/x-icon"),
    JPEG(".jpg", "image/jpeg"),
    JS(".js", "text/javascript"),
    PNG(".png", "image/png"),
    TTF(".ttf", "font/ttf"),
    WOFF(".woff", "font/woff");


    private final String extension;
    private final String type;

    HttpMIME(String extension, String type) {
        this.extension = extension;
        this.type = type;
    }

    public String getExtension() {
        return extension;
    }

    public String getType() {
        return type;
    }

    public static HttpMIME findBy(String extension) throws IllegalArgumentException {
        return Arrays.stream(HttpMIME.values())
                .filter(httpMIME -> httpMIME.getExtension().equals(extension))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(extension + "는 존재하지 않은 MIME 입니다."));
    }
}
