package webserver.http;

import java.util.Arrays;
import java.util.Optional;

public enum MIME {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JS("js", "application/javascript"),
    ICO("ico", "image/x-icon"),
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    TXT("txt", "text/plain"),
    WOFF("woff", "font/WOFF"),
    TTF("ttf", "font/ttf");

    private String extension;
    private String contentType;

    MIME(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static MIME from(String token) {
        Optional<MIME> optionalMime = Arrays.stream(MIME.values())
                .filter(mime -> mime.extension.equals(token))
                .findFirst();
        return optionalMime.orElse(defaultMime());
    }

    public static MIME defaultMime() {
        return HTML;
    }

    public String getContentType() {
        return contentType;
    }
}
