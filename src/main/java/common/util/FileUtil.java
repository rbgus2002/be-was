package common.util;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static byte[] get(final URL resource) throws IOException {
        if (resource == null) {
            return null;
        }
        return Files.readAllBytes(Path.of(resource.getPath()));
    }
}
