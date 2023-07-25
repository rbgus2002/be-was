package utils;

import exception.BadRequestException;
import http.HttpResponse;
import http.HttpStatus;
import http.MIME;
import view.Page;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static exception.ExceptionList.INVALID_URI;
import static http.Extension.HTML;
import static http.FilePath.ERROR;

public class FileIOUtils {
    public static final String STATIC_RESOURCES = "src/main/resources/static";
    public static final String TEMPLATES_RESOURCES = "src/main/resources/templates";
    private static final Page page = new Page();

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
            throw new BadRequestException(INVALID_URI);
        } catch (BadRequestException e) {
            String errorPage = page.getErrorPage(e.getMessage());
            return loadFileFromString(HttpStatus.NOT_FOUND, errorPage, ERROR)
                    .setContentType(MIME.getMIME().get(HTML));
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
