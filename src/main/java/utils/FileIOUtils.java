package utils;

import http.HttpResponse;
import http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIOUtils {
    public static final String STATIC_RESOURCES = "src/main/resources/static";
    public static final String TEMPLATES_RESOURCES = "src/main/resources/templates";

    public static HttpResponse.ResponseBuilder loadStaticFromPath(HttpStatus httpStatus, String uri) {
        Path path = Paths.get(STATIC_RESOURCES + uri);
        try {
            if (Files.exists(path)) {
                return new HttpResponse.ResponseBuilder()
                        .setStatus(httpStatus)
                        .setLocation(uri)
                        .setBody(Files.readAllBytes(new File(STATIC_RESOURCES + uri).toPath()));
            }
            return new HttpResponse.ResponseBuilder()
                    .setStatus(httpStatus)
                    .setLocation("/wrong_access.html")
                    .setBody(Files.readAllBytes(new File(TEMPLATES_RESOURCES + "/wrong_access.html").toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse.ResponseBuilder loadTemplatesFromPath(HttpStatus httpStatus, String uri) {
        Path path = Paths.get(TEMPLATES_RESOURCES + uri);
        try {
            if (Files.exists(path)) {
                return new HttpResponse.ResponseBuilder()
                        .setStatus(httpStatus)
                        .setLocation(uri)
                        .setBody(Files.readAllBytes(new File(TEMPLATES_RESOURCES + uri).toPath()));
            }
            return new HttpResponse.ResponseBuilder()
                    .setStatus(httpStatus)
                    .setLocation("/wrong_access.html")
                    .setBody(Files.readAllBytes(new File(TEMPLATES_RESOURCES + "/wrong_access.html").toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
