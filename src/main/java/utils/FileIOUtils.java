package utils;

import http.HttpResponse;
import http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static http.FilePath.WRONG_ACCESS;

public class FileIOUtils {
    public static final String STATIC_RESOURCES = "src/main/resources/static";
    public static final String TEMPLATES_RESOURCES = "src/main/resources/templates";

    public static HttpResponse.ResponseBuilder loadStaticFromPath(HttpStatus httpStatus, String uri) {
        return loadFromPath(STATIC_RESOURCES, httpStatus, uri);
    }

    public static HttpResponse.ResponseBuilder loadTemplatesFromPath(HttpStatus httpStatus, String uri) {
        return loadFromPath(TEMPLATES_RESOURCES, httpStatus, uri);
    }

    private static HttpResponse.ResponseBuilder loadFromPath(String resourcePath, HttpStatus httpStatus, String uri) {
        Path path = Paths.get(resourcePath + uri);
        try {
            if (Files.exists(path)) {
                return new HttpResponse.ResponseBuilder()
                        .setStatus(httpStatus)
                        .setLocation(uri)
                        .setBody(Files.readAllBytes(new File(resourcePath + uri).toPath()));
            }
            return new HttpResponse.ResponseBuilder()
                    .setStatus(HttpStatus.NOT_FOUND)
                    .setLocation(WRONG_ACCESS)
                    .setBody(Files.readAllBytes(new File(resourcePath + WRONG_ACCESS).toPath()));
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

}
