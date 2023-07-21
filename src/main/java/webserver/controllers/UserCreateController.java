package webserver.controllers;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static service.UserService.addUser;
import static webserver.http.enums.ContentType.HTML;
import static webserver.http.enums.HttpResponseStatus.BAD_REQUEST;
import static webserver.http.enums.HttpResponseStatus.FOUND;

public class UserCreateController implements Controller {
    private final static Logger logger = LoggerFactory.getLogger(UserCreateController.class);

    @Override
    public HttpResponse handle(HttpRequest request) {
        Map<String, String> parameters = parseUri(request.uri());

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
                .setHeader("Location", path)
                .build();
    }

    private boolean verifyParameter(Map<String, String> parameters) {
        Set<String> essentialField = Set.of("userId", "password", "name", "email");
        if (!parameters.keySet().equals(essentialField)) {
            return false;
        }
        return true;
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
