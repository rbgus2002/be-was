package utils;

import exception.BadRequestException;
import http.HttpResponse;
import http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static exception.ExceptionList.INVALID_URI;

public class FileUtils {
    public static final String STATIC_DIRECTORY = "src/main/resources/static";
    public static final String TEMPLATES_DIRECTORY = "src/main/resources/templates";

    public static HttpResponse.ResponseBuilder loadFromPath(HttpStatus httpStatus, String uri) {
        if (!isFileExist(uri))
            throw new BadRequestException(INVALID_URI);
        String filePath = isTemplateFile(uri) ? TEMPLATES_DIRECTORY + uri : STATIC_DIRECTORY + uri;
        try {
            return new HttpResponse.ResponseBuilder()
                    .setStatus(httpStatus)
                    .setLocation(uri)
                    .setBody(Files.readAllBytes(new File(filePath).toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse.ResponseBuilder loadFileFromString(HttpStatus httpStatus, String html, String location) {
        return new HttpResponse.ResponseBuilder()
                .setStatus(httpStatus)
                .setLocation(location)
                .setBody(html.getBytes(StandardCharsets.UTF_8));
    }

    private static boolean isFileExist(String uri) {
        return isTemplateFile(uri) || isStaticFile(uri);
    }

    private static boolean isTemplateFile(String uri) {
        Path path = Paths.get(TEMPLATES_DIRECTORY + uri);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    private static boolean isStaticFile(String uri) {
        Path path = Paths.get(STATIC_DIRECTORY + uri);
        return Files.exists(path) && !Files.isDirectory(path);
    }

}
