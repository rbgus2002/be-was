package webserver.utils;

public class FileExtensionSeparator {
    private FileExtensionSeparator() {
    }

    public static String separateExtension(String fileName) {
        String[] tokens = fileName.split("\\.");
        return tokens[tokens.length - 1];
    }
}
