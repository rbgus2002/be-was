package global.constant;

import exception.NotFoundExtensionException;

import java.util.Arrays;

public enum ContentType {
    HTML("html", "text/html");

    private final String fileType;
    private final String contentType;

    ContentType(String fileType, String contentType) {
        this.fileType = fileType;
        this.contentType = contentType;
    }

    public static ContentType findContentType(String fileType) {
        return Arrays.stream(ContentType.values())
                .filter(type -> type.fileType.equalsIgnoreCase(fileType))
                .findFirst()
                .orElseThrow(NotFoundExtensionException::new);
    }

    public String getContentType() {
        return this.contentType;
    }
}
