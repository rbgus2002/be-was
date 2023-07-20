package webserver.controllers;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static db.Database.addUser;

public class UserCreateController implements Controller {
    private final static Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> parameters = parseUri(request.uri());
        HttpResponse.Builder builder = HttpResponse.newBuilder();

        // TODO: verifyUser 로직 구현
        if (parameters.values().size() != 4) {
            return Controller.createErrorResponse(request, 400);
        }

        if (parameters.get("userId") == null || parameters.get("password") == null
                || parameters.get("name") == null || parameters.get("email") == null)
            return Controller.createErrorResponse(request, 400);


        User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
        logger.info("User info: userId: {}, password: {}, name: {}, email: {}", user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
        addUser(user);

        builder.version(request.version())
                .statusCode(200)
                .contentType(request.contentType())
                .body(user.toString().getBytes());

        return builder.build();
    }

    private Map<String, String> parseUri(String uri) {
        Map<String, String> result = new HashMap<>();
        if (!uri.contains("?") || uri.indexOf("?") == uri.length() - 1) {
            return result;
        }
        String paramString = uri.substring(uri.indexOf("?") + 1);

        String[] parameters = paramString.split("&");
        if (parameters.length < 4) {
            return result;
        }
        for (String parameter : parameters) {
            int splitIndex = parameter.indexOf("=");
            if (splitIndex < 0 || splitIndex == parameter.length() - 1) break;
            String value = URLDecoder.decode(parameter.substring(splitIndex + 1));
            result.put(parameter.substring(0, splitIndex), value);
        }

        return result;
    }
}
