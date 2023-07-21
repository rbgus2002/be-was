package webserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.enums.ContentType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static webserver.http.enums.ContentType.HTML;
import static webserver.http.enums.ContentType.getContentTypeByExtension;
import static webserver.http.enums.HttpResponseStatus.NOT_FOUND;
import static webserver.http.enums.HttpResponseStatus.OK;

public class StaticFileController implements Controller {
    private final static Logger logger = LoggerFactory.getLogger(StaticFileController.class);

    @Override
    public HttpResponse handle(HttpRequest request) {
        String extension = request.uri().substring(request.uri().lastIndexOf("."));
        ContentType contentType = getContentTypeByExtension(extension);
        String path = getPathString(request, contentType);

        HttpResponse.Builder builder = HttpResponse.newBuilder();

        byte[] body;
        try {
            body = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return createErrorResponse(request, NOT_FOUND);
        }

        builder.version(request.version())
                .status(OK)
                .contentType(contentType)
                .body(body);

        return builder.build();
    }

    private String getPathString(HttpRequest request, ContentType contentType) {
        String fileName = request.uri();

        if (contentType == HTML) {
            return System.getProperty("user.dir").concat("/src/main/resources/templates").concat(fileName);
        }
        return System.getProperty("user.dir").concat("/src/main/resources/static").concat(fileName);
    }
}
