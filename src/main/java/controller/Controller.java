package controller;

import http.*;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

import static exception.Exception.INVALID_URI;
import static http.Extension.HTML;
import static http.HttpMethod.POST;
import static utils.FileIOUtils.*;

public class Controller {
    private final UserService userService = new UserService();

    public HttpResponse.ResponseBuilder loadFileByRequest(HttpRequest httpRequest) {
        try {
            if (httpRequest.getMethod().equals(POST)) {
                return routeByUriWithBody(httpRequest);
            }
            String uri = httpRequest.getUri();
            if (uri.contains("?")) {
                return routeByUriWithQuestion(uri);
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
        } catch (Exception e) {
            return loadTemplatesFromPath(HttpStatus.NOT_FOUND, "/error.html")
                    .setContentType(MIME.getMIME().get(HTML));
        }

    }

    public HttpResponse.ResponseBuilder routeByUriWithQuestion(String uri) {
        String[] apis = uri.split("\\?");
        if (apis[0].equals("/user/create")) {
            return createUser(parseParams(apis[1]));
        }
        throw new IllegalArgumentException(INVALID_URI);
    }

    public HttpResponse.ResponseBuilder routeByUriWithBody(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        if (uri.equals("/user/create")) {
            return createUser(parseParams(httpRequest.getBody()));
        }
        throw new IllegalArgumentException(INVALID_URI);
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
        return loadTemplatesFromPath(HttpStatus.FOUND, "/index.html");
    }

}
