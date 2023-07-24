package webserver.Constants;

public enum ContentType {

    HTML("text/html"),
    CSS("text/css"),
    JS("application/javascript"),
    ICO("image/x-icon"),
    PNG("image/png"),
    JPG("image/jpeg"),
    JPEG("image/jpeg");

    private final String description;

    ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
