package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileIOUtils {
    public static final String HTML = "html";
    public static final String CSS = "css";
    public static final String STATIC_RESOURCES = "src/main/resources/static";
    public static final String TEMPLATES_RESOURCES = "src/main/resources/templates";

    public static byte[] loadStaticFromPath(String uri) throws IOException {
        return Files.readAllBytes(new File(STATIC_RESOURCES + uri).toPath());
    }

    public static byte[] loadTemplatesFromPath(String uri) throws IOException {
        return Files.readAllBytes(new File(TEMPLATES_RESOURCES + uri).toPath());
    }

}
