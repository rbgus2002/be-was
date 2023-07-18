package webserver.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtils {
    public static final String TEMPLATES_DIRECTORY = "src/main/resources/templates";
    public static final String STATIC_DIRECTORY = "src/main/resources/static";

    private FileUtils() {
    }

    public static boolean isFileExist(String URI) {
        return isTemplateFile(URI) || isStaticFile(URI);
    }

    public static boolean isTemplateFile(String URI) {
        Path path = Paths.get(TEMPLATES_DIRECTORY + URI);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    public static boolean isStaticFile(String URI) {
        Path path = Paths.get(STATIC_DIRECTORY + URI);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    public static byte[] readBytesFromFile(Path path) throws IOException {
        InputStream inputStream = Files.newInputStream(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

}
