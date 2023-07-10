package util;

import java.io.InputStream;
import java.net.URI;
import java.util.Objects;

public class FileUtils {
    private FileUtils() {
    }

    public static InputStream getResourceAsStream(URI requestUrl) {
        String[] split = requestUrl.toString().split("\\.");
        String ext = split[split.length - 1];
        return FileUtils.class.getResourceAsStream((Objects.equals(ext, "html") ? "/templates/" : "/static/") + requestUrl);
    }

}
