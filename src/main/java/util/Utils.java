package util;

import java.nio.file.Path;

public class Utils {

    private Utils() {
    }

    public static String getMimeType(Path path) {
        String filename = path.getFileName().toString();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        switch (extension) {
            case "css":
                return "text/css";
            case "js":
                return "text/javascript";
            case "html":
                return "text/html; charset=utf-8";
            case "ico":
                return "image/x-icon";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return  "image/png";
            case "txt":
                return "text/plain";
            default:
                return "application/octet-stream";
        }
    }
}
