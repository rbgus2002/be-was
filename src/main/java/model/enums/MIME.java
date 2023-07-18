package model.enums;

public enum MIME {
    HTML("text/html"),
    CSS("text/css"),
    JS("text/js"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpg"),
    JSON("application/json"),
    ;

    private final String contentType;

    MIME(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public boolean isHTML() {
        return this == HTML;
    }
}
