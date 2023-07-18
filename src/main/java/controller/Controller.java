package controller;

import http.ContentType;
import http.HttpResponse;
import service.UserService;

import http.HttpRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.FileIOUtils.*;

public class Controller {
    private final UserService userService = new UserService();

    public HttpResponse.ResponseBuilder loadFileByRequest(HttpRequest httpRequest) throws IOException {
        String uri = httpRequest.getUri();
        String[] uris = uri.split("\\.");
        switch (uris[uris.length - 1]) {
            case HTML:
                return loadTemplatesFromPath(uri)
                        .setContentType(ContentType.HTML);
            case CSS:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.CSS);
            case JS:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.JS);
            case ICO:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.ICO);
            case PNG:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.PNG);
            case JPG:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.JPG);
            case EOT:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.EOT);
            case SVG:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.SVG);
            case TTF:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.TTF);
            case WOFF:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.WOFF);
            case WOFF2:
                return loadStaticFromPath(uri)
                        .setContentType(ContentType.WOFF2);
            default:
                return routeByUri(uri);
        }
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
            return loadTemplatesFromPath("/user/signup_success.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
