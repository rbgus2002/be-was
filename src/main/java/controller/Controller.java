package controller;

import http.HttpStatus;
import service.UserService;

import http.HttpRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.FileIOUtils.*;

public class Controller {
    private final UserService userService = new UserService();

    public Map<HttpStatus, byte[]> loadFileByRequest(HttpRequest httpRequest) throws IOException {
        String uri = httpRequest.getUri();
        String[] uris = uri.split("\\.");
        switch (uris[uris.length - 1]) {
            case HTML:
                return loadTemplatesFromPath(uri);
            case CSS:
                return loadStaticFromPath(uri);
            default:
                return routeByUri(uri);
        }
    }

    public Map<HttpStatus, byte[]> routeByUri(String uri) {
        String[] apis = uri.split("\\?");
        if (apis[0].equals("/user/create")) {
            return createUser(parseParams(apis[1]));
        }
        return null;
    }

    private Map<String, String> parseParams(String parameter) {
        String[] params = parameter.split("&");
        Map<String, String> information = new HashMap<>();
        for (String param : params) {
            String[] info = param.split("=");
            information.put(info[0], info[1]);
        }
        return information;
    }

    public Map<HttpStatus, byte[]> createUser(Map<String, String> parameters) {
        userService.createUser(parameters);
        try {
            return loadTemplatesFromPath("/user/signup_success.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
