package webserver.utils;

import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtils {
    public static final String TEMPLATES_DIRECTORY = "src/main/resources/templates";
    public static final String STATIC_DIRECTORY = "src/main/resources/static";

    private FileUtils() {
    }

    public static void processFileResponse(String URI, HttpResponse httpResponse) throws IOException {
        URI = preprocessURI(URI);
        byte[] body = readFileBytes(URI);
        HttpStatus status = resolveHttpStatus(URI);
        String contentType = ContentTypeResolver.getContentType(URI);

        httpResponse.setStatus(status);
        httpResponse.set(HttpField.CONTENT_TYPE, contentType);
        httpResponse.set(HttpField.CONTENT_LENGTH, body.length);
        httpResponse.setBody(body);
    }

    private static String preprocessURI(String URI) {
        if (isFileExist(URI)) {
            return URI;
        }
        if (URI.equals("/")) {
            return "/index.html";
        }
        return "/404.html";
    }

    public static byte[] readFileBytes(String URI) throws IOException {
        if (FileUtils.isStaticFile(URI)) {
            return Files.readAllBytes(Paths.get(STATIC_DIRECTORY + URI));
        }
        return Files.readAllBytes(Paths.get(TEMPLATES_DIRECTORY + URI));
    }

    private static HttpStatus resolveHttpStatus(String URI) {
        if (URI.equals("/404.html")) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }

    private static boolean isFileExist(String URI) {
        return isTemplateFile(URI) || isStaticFile(URI);
    }

    private static boolean isTemplateFile(String URI) {
        Path path = Paths.get(TEMPLATES_DIRECTORY + URI);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    private static boolean isStaticFile(String URI) {
        Path path = Paths.get(STATIC_DIRECTORY + URI);
        return Files.exists(path) && !Files.isDirectory(path);
    }
}
