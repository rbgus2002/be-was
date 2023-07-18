package util;

public class StringUtils {

    private StringUtils() {
    }

    public static String getExtension(String path) {
        String[] split = path.split("\\.");
        return split[split.length - 1];
    }
}
