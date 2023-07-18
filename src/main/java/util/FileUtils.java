package util;

public class FileUtils {
    private FileUtils() {
    }

    public static String getExtension(String path) {
        String[] split = path.split("\\.");
        return split[split.length - 1];
    }
}
