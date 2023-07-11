package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileIOUtils {
    public static final String STATIC_RESOURCES = "src/main/resources/static/";
    public static final String TEMPLATES_RESOURCES = "src/main/resources/templates";
    private String splitPath(String requestLine) {
        return requestLine.split(" ")[1];
    }
    public byte[] loadStaticFromPath(String requestLine) throws IOException {
        return Files.readAllBytes(new File(STATIC_RESOURCES + splitPath(requestLine)).toPath());
    }
    public byte[] loadTemplatesFromPath(String requestLine) throws IOException {
        return Files.readAllBytes(new File(TEMPLATES_RESOURCES + splitPath(requestLine)).toPath());
    }

}
