package http;

import utils.Parser;

import java.util.Arrays;

public enum MIME {
    CSS ("text/css"),
    JS("text/javascript"),
    ICO("image/x-icon"),
    HTML("text/html"),
    PNG("image/png"),
    JPG("image/jpeg"),
    TTF("font/ttf"),
    WOFF("font/woff");

    private final String contentType;

    MIME(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public static MIME findMIME(String requestUri) {
        String extension = Parser.parseExtension(requestUri);
        return Arrays.stream(MIME.values())
                .filter(mime -> mime.toString().equals(extension))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("올바른 확장자가 아닙니다"));
    }
}
