package webserver.http;

import java.util.Arrays;
import java.util.Optional;

public enum MIME {
    HTML("text/html"),
    CSS("text/css"),
    JS("application/javascript"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpeg"),
    TXT("text/plain"),
    WOFF("font/WOFF"),
    TTF("font/ttf");

    private String contentType;

    MIME(String contentType) {
        this.contentType = contentType;
    }

    public static MIME from(String token) {
        Optional<MIME> optionalMime = Arrays.stream(MIME.values())
                .filter(mime -> mime.getContentType().equals(token))
                .findFirst();
        return optionalMime.orElse(HTML);
    }

    public static MIME defaultMime() {
        return HTML;
    }

    public String getContentType() {
        return contentType;
    }
}
