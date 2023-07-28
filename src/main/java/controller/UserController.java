package controller;

import exception.BadRequestException;
import http.*;
import service.UserService;

import java.util.Map;

import static exception.ExceptionList.INVALID_URI;
import static http.FilePath.INDEX;
import static utils.FileUtils.loadFromPath;

public class UserController extends Controller {
    private final UserService userService = new UserService();

    private UserController() {
    }

    private static class Holder {
        private static final UserController INSTANCE = new UserController();
    }

    public static UserController getInstance() {
        return UserController.Holder.INSTANCE;
    }

    @Override
    public HttpResponse.ResponseBuilder doGet(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.equals("/user/create") && httpRequest.getQueryString() != null) {
            return createUser(httpRequest.getQueryString());
        }
        throw new BadRequestException(INVALID_URI);
    }

    @Override
    public HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.equals("/user/create")) {
            return createUser(httpRequest.getBody());
        }
        if (uri.equals("/user/login")) {
            return loginUser(httpRequest.getBody());
        }
        if (uri.equals("/user/logout")) {
            return logoutUser(httpRequest.getSessionId());
        }
        throw new BadRequestException(INVALID_URI);
    }

    private HttpResponse.ResponseBuilder createUser(Map<String, String> parameters) {
        userService.createUser(parameters);
        return loadFromPath(HttpStatus.FOUND, INDEX);
    }

    private HttpResponse.ResponseBuilder loginUser(Map<String, String> parameters) {
        String sessionId = userService.loginUser(parameters);
        return loadFromPath(HttpStatus.FOUND, INDEX)
                .setSessionId(sessionId);
    }

    private HttpResponse.ResponseBuilder logoutUser(String sessionId) {
        userService.logoutUser(sessionId);
        return loadFromPath(HttpStatus.FOUND, INDEX)
                .setSessionId("");
    }

}
