package utils;

public class FileIOUtils {
    public static final String STATIC_RESOURCES = "src/main/resources/static";
    public static final String TEMPLATES_RESOURCES = "src/main/resources/templates";
    public static String splitPath(String requestLine) {
        return requestLine.split(" ")[1];
    }

}
