package webserver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static final String TEMPLATES_PATH = "src/main/resources/templates/";
    public static final String STATIC_PATH = "src/main/resources/static/";

    private FileUtils() {
    }

    public static byte[] readFile(String path) {
        try {
            return Files.readAllBytes(new File(path).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static byte[] readFileFromTemplate(String path) {
        path = TEMPLATES_PATH + path;
        try {
            return Files.readAllBytes(new File(path).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static byte[] readFileFromStatic(String path) {
        path = STATIC_PATH + path;
        try {
            return Files.readAllBytes(new File(path).toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
