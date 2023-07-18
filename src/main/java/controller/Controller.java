package controller;

import http.*;
import service.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static http.Extension.HTML;
import static utils.FileIOUtils.*;

public class Controller {
    private final UserService userService = new UserService();

    public HttpResponse.ResponseBuilder loadFileByRequest(HttpRequest httpRequest) throws IOException {
        String uri = httpRequest.getUri();
        if (uri.contains("?")) {
            return routeByUri(uri);
        }
        String[] uris = uri.split("\\.");
        String extension = uris[uris.length - 1];
        if (MIME.getMIME().entrySet().stream().noneMatch(entry -> entry.getKey().equals(extension))) {
            return loadTemplatesFromPath(HttpStatus.NOT_FOUND, "/wrong_access.html");
        }
        if (extension.equals(HTML)) {
            return loadTemplatesFromPath(HttpStatus.OK, uri)
                    .setContentType(MIME.getMIME().get(HTML));
        }
        return loadStaticFromPath(HttpStatus.OK, uri)
                    .setContentType(MIME.getMIME().get(extension));
    }

    public HttpResponse.ResponseBuilder routeByUri(String uri) {
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

    public HttpResponse.ResponseBuilder createUser(Map<String, String> parameters) {
        userService.createUser(parameters);
        try {
            return loadTemplatesFromPath(HttpStatus.OK, "/user/signup_success.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
