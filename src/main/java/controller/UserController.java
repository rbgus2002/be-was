package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import webserver.HttpRequest;

import java.util.Map;

public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    public static String createUser(HttpRequest httpRequest) {
        String requestPath = httpRequest.getRequestPath();
        String queryString = requestPath.split("\\?")[1];
        Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);

        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
        logger.debug("User: {}", user);

        requestPath = "/index.html";

        return requestPath;
    }
}
