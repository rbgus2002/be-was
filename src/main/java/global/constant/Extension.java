package global.constant;

import exception.NotFoundExtensionException;

import java.util.Arrays;

public enum Extension {
    HTML("html");

    private final String fileType;

    Extension(String fileType) {
        this.fileType = fileType;
    }

    public static Extension findExtensionType(String fileType) {
        return Arrays.stream(Extension.values())
                .filter(type -> type.fileType.equalsIgnoreCase(fileType))
                .findFirst()
                .orElseThrow(NotFoundExtensionException::new);
    }
}