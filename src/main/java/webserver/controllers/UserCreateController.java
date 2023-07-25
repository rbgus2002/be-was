package webserver.controllers;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controllers.annotations.RequestMethod;
import webserver.controllers.annotations.RequestPath;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import static service.UserService.addUser;
import static webserver.http.enums.ContentType.HTML;
import static webserver.http.enums.HttpResponseStatus.*;

@RequestPath(path = "/user/create")
public class UserCreateController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @RequestMethod(method = "GET")
    public HttpResponse handleGet(HttpRequest request) {
        Map<String, String> parameters = request.uri().getParameters();

        if (!verifyParameter(parameters)) {
            return createErrorResponse(request, BAD_REQUEST);
        }

        HttpResponse.Builder builder = HttpResponse.newBuilder();

        User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
        logger.info("User info: userId: {}, password: {}, name: {}, email: {}", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());

        String path = "http://".concat(request.getHeader("Host").concat("/index.html"));
        if (!addUser(user)) {
            path = "http://".concat(request.getHeader("Host").concat("/user/form.html"));
        }

        return builder.version(request.version())
                .status(FOUND)
                .contentType(HTML)
                .setHeader("Location: ".concat(path))
                .build();
    }

    @RequestMethod(method = "POST")
    public HttpResponse handlePost(HttpRequest request) throws IOException {
//        Map<String, String> parameters = request.uri().getParameters();
//
//        if (!verifyParameter(parameters)) {
//            return createErrorResponse(request, BAD_REQUEST);
//        }

        HttpResponse.Builder builder = HttpResponse.newBuilder();

//        User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
//        logger.info("User info: userId: {}, password: {}, name: {}, email: {}", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
//
//        String path = "http://".concat(request.getHeader("Host").concat("/index.html"));
//        if (!addUser(user)) {
//            path = "http://".concat(request.getHeader("Host").concat("/user/form.html"));
//        }

        return builder.version(request.version())
                .status(OK)
                .contentType(HTML)
                .body(Files.readAllBytes(Path.of(System.getProperty("user.dir").concat("/src/main/resources/templates/index.html"))))
                .build();
    }

    private boolean verifyParameter(Map<String, String> parameters) {
        Set<String> essentialField = Set.of("userId", "password", "name", "email");
        if (!parameters.keySet().equals(essentialField)) {
            return false;
        }
        for (String field : essentialField) {
            if ("".equals(parameters.get(field)))
                return false;
        }
        return true;
    }
}
