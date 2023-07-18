package utils;

import http.HttpResponse;
import http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIOUtils {
    public static final String HTML = "html";
    public static final String CSS = "css";
    public static final String JS = "js";
    public static final String ICO = "ico";
    public static final String PNG = "png";
    public static final String JPG = "jpg";
    public static final String EOT = "eot";
    public static final String SVG = "svg";
    public static final String TTF = "ttf";
    public static final String WOFF = "woff";
    public static final String WOFF2 = "woff2";
    public static final String STATIC_RESOURCES = "src/main/resources/static";
    public static final String TEMPLATES_RESOURCES = "src/main/resources/templates";

    public static HttpResponse.ResponseBuilder loadStaticFromPath(HttpStatus httpStatus, String uri) throws IOException {
        Path path = Paths.get(STATIC_RESOURCES + uri);
        if (Files.exists(path)) {
            return new HttpResponse.ResponseBuilder()
                    .setStatus(httpStatus)
                    .setBody(Files.readAllBytes(new File(STATIC_RESOURCES + uri).toPath()));
        }
        return new HttpResponse.ResponseBuilder()
                .setStatus(httpStatus)
                .setBody(Files.readAllBytes(new File(TEMPLATES_RESOURCES + "/wrong_access.html").toPath()));
    }

    public static HttpResponse.ResponseBuilder loadTemplatesFromPath(HttpStatus httpStatus, String uri) throws IOException {
        Path path = Paths.get(TEMPLATES_RESOURCES + uri);
        if (Files.exists(path)) {
            return new HttpResponse.ResponseBuilder()
                    .setStatus(httpStatus)
                    .setBody(Files.readAllBytes(new File(TEMPLATES_RESOURCES + uri).toPath()));
        }
        return new HttpResponse.ResponseBuilder()
                .setStatus(httpStatus)
                .setBody(Files.readAllBytes(new File(TEMPLATES_RESOURCES + "/wrong_access.html").toPath()));
    }

}
