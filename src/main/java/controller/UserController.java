package controller;

import exception.BadRequestException;
import http.*;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

import static exception.ExceptionList.INVALID_URI;
import static http.FilePath.INDEX;
import static utils.FileIOUtils.loadFromPath;
import static utils.StringUtils.decodeBody;

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

    public HttpResponse.ResponseBuilder doGet(String uri) {
        String[] apis = uri.split("\\?");
        if (apis[0].equals("/user/create") && apis.length == 2) {
            return createUser(parseParams(apis[1]));
        }
        throw new BadRequestException(INVALID_URI);
    }

    public HttpResponse.ResponseBuilder doPost(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.equals("/user/create")) {
            return createUser(parseParams(httpRequest.getBody()));
        }
        if (uri.equals("/user/login")) {
            return loginUser(parseParams(httpRequest.getBody()));
        }
        if (uri.equals("/user/logout")) {
            return logoutUser(httpRequest.getSessionId());
        }
        throw new BadRequestException(INVALID_URI);
    }

    private Map<String, String> parseParams(String parameter) {
        String[] params = parameter.split("&");
        Map<String, String> information = new HashMap<>();
        for (String param : params) {
            String[] info = param.split("=");
            if (info.length != 2)
                throw new BadRequestException(INVALID_URI);
            information.put(info[0], decodeBody(info[1]));
        }
        return information;
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
