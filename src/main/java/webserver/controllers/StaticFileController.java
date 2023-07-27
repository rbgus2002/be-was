package webserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controllers.annotations.RequestMethod;
import webserver.controllers.annotations.RequestPath;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Session;
import webserver.http.enums.ContentType;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static service.SessionService.getSession;
import static webserver.http.Cookie.isValidCookie;
import static webserver.http.enums.ContentType.HTML;
import static webserver.http.enums.ContentType.getContentTypeOfFile;
import static webserver.http.enums.HttpResponseStatus.NOT_FOUND;
import static webserver.http.enums.HttpResponseStatus.OK;

@RequestPath(path = "/")
public class StaticFileController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(StaticFileController.class);
    private static final Map<String, String> requestAttribute = new HashMap<>(){{
        put("${logout}", "style=\"display:none;\"");
        put("${login}", "");
        put("${user}", "");
    }};

    @RequestMethod(method = "GET")
    public HttpResponse handleGet(HttpRequest request) {
        String fileName = request.uri().getPath();
        ContentType contentType = getContentTypeOfFile(fileName);
        String filePath = getPathString(fileName, contentType);

        if (!Files.exists(Paths.get(filePath)))
            return createErrorResponse(request, NOT_FOUND);

        HttpResponse.Builder builder = HttpResponse.newBuilder();

        Map<String, String> attributes = setRequestAttribute(request, builder);

        return builder.version(request.version())
                .status(OK)
                .fileName(filePath)
                // todo: 동적 HTML을 위한 attribute 설정
                .setAttribute(attributes)
                .build();
    }

    private Map<String, String> setRequestAttribute(HttpRequest request, HttpResponse.Builder builder) {
        if(!isValidCookie(request.cookie())) {
            return requestAttribute;
        }
        Session session = getSession(request.cookie().getSessionId());
        Map<String, String> userAttribute = new HashMap<>();

        userAttribute.put("${logout}", "");
        userAttribute.put("${login}", "style=\"display:none;\"");
        userAttribute.put("${user}", "<li style=\"pointer-events: none;\" ><a>" + session.getUser().getName() + " 님</a></li>");

        return userAttribute;
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

    private String getPathString(String fileName, ContentType contentType) {
        if (contentType == HTML) {
            return "src/main/resources/templates".concat(fileName);
        }
        return "src/main/resources/static".concat(fileName);
    }
}
