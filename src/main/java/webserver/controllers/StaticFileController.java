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

import static service.SessionService.getSession;
import static webserver.http.Cookie.isValidCookie;
import static webserver.http.enums.ContentType.HTML;
import static webserver.http.enums.ContentType.getContentTypeByExtension;
import static webserver.http.enums.HttpResponseStatus.NOT_FOUND;
import static webserver.http.enums.HttpResponseStatus.OK;

@RequestPath(path = "/")
public class StaticFileController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(StaticFileController.class);

    @RequestMethod(method = "GET")
    public HttpResponse handleGet(HttpRequest request) {
        String extension = request.uri().getExtension();
        ContentType contentType = getContentTypeByExtension(extension);
        String path = getPathString(request, contentType);

        HttpResponse.Builder builder = HttpResponse.newBuilder();

        byte[] body;
        String fileContent;
        try {
            fileContent = Files.readString(Paths.get(path));
            body = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return createErrorResponse(request, NOT_FOUND);
        }

        if(isValidCookie(request.cookie()))
            body = reviseContentWithUserInfo(request, fileContent);

        builder.version(request.version())
                .status(OK)
                .contentType(contentType)
                .body(body);

        return builder.build();
    }

    private byte[] reviseContentWithUserInfo(HttpRequest request, String fileContent) {
        Session session = getSession(request.cookie().getSessionId());
        return fileContent
                .replace("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>",
                        "<li style=\"pointer-events: none;\" ><a>" + session.getUser().getName() + " 님</a></li>")
                .replace("<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>",
                        "<li style=\"pointer-events: none;\" ><a>" + session.getUser().getName() + " 님</a></li>")
                .replace("<li><a href=\"user/logout\" style=\"display: none;\" role=\"button\">로그아웃</a></li>",
                        "<li><a href=\"user/logout\" role=\"button\">로그아웃</a></li>")
                .getBytes();
    }

    private String getPathString(HttpRequest request, ContentType contentType) {
        String fileName = request.uri().getPath();

        if (contentType == HTML) {
            return System.getProperty("user.dir").concat("/src/main/resources/templates").concat(fileName);
        }
        return System.getProperty("user.dir").concat("/src/main/resources/static").concat(fileName);
    }
}
