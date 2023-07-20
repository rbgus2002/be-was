package util;

import java.nio.file.Path;

public class Utils {

    private Utils() {
    }

    public static String getMimeType(Path path) {
        String filename = path.getFileName().toString();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        String contentType;
        switch (extension) {
            case "css":
                contentType = "text/css";
                break;
            case "js":
                contentType = "text/javascript";
                break;
            case "html":
                contentType = "text/html; charset=utf-8";
                break;
            case "ico":
                contentType = "image/x-icon";
                break;
            case "jpg":
            case "jpeg":
                contentType = "image/jpeg";
                break;
            case "png":
                contentType = "image/png";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }
        return contentType;
    }
}
