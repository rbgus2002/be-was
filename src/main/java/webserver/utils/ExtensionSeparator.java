package webserver.utils;

public class ExtensionSeparator {
    private ExtensionSeparator() {
    }

    public static String separateExtension(String fileName) {
        String[] tokens = fileName.split("\\.");
        return tokens[tokens.length - 1];
    }
}
