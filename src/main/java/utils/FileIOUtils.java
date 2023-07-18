package utils;

import http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FileIOUtils {
    public static final String HTML = "html";
    public static final String CSS = "css";
    public static final String STATIC_RESOURCES = "src/main/resources/static";
    public static final String TEMPLATES_RESOURCES = "src/main/resources/templates";

    public static Map<HttpStatus, byte[]> loadStaticFromPath(String uri) throws IOException {
        Map<HttpStatus, byte[]> response = new HashMap<>();
        response.put(HttpStatus.OK, Files.readAllBytes(new File(STATIC_RESOURCES + uri).toPath()));
        return response;
    }

    public static Map<HttpStatus, byte[]> loadTemplatesFromPath(String uri) throws IOException {
        Map<HttpStatus, byte[]> response = new HashMap<>();
        response.put(HttpStatus.OK, Files.readAllBytes(new File(TEMPLATES_RESOURCES + uri).toPath()));
        return response;
    }

}
