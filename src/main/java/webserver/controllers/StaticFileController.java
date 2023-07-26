package webserver.controllers;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controllers.annotations.RequestMethod;
import webserver.controllers.annotations.RequestPath;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Session;
import webserver.http.enums.ContentType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static db.Sessions.getSession;
import static webserver.http.enums.ContentType.HTML;
import static webserver.http.enums.ContentType.getContentTypeByExtension;
import static webserver.http.enums.HttpResponseStatus.NOT_FOUND;
import static webserver.http.enums.HttpResponseStatus.OK;

@RequestPath(path = "/")
public class StaticFileController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(StaticFileController.class);

    @RequestMethod(method = "GET")
    public HttpResponse handleGet(HttpRequest request) {
        if(request.cookie() != null) {
            Session session = getSession(request.cookie().getSessionId());
            logger.debug("session Id: {}", session.getSessionId());
            User user = session.getUser();
            logger.debug("session User: userId = {}, email = {}", user.getUserId(), user.getEmail());
        }
        String extension = request.uri().getExtension();
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
        String fileName = request.uri().getPath();

        if (contentType == HTML) {
            return System.getProperty("user.dir").concat("/src/main/resources/templates").concat(fileName);
        }
        return System.getProperty("user.dir").concat("/src/main/resources/static").concat(fileName);
    }
}
