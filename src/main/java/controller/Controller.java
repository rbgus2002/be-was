package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import webserver.http.HttpRequest;

import java.util.Map;

public class Controller {

    private final static Logger logger = LoggerFactory.getLogger(Controller.class);

    public static String createUser(HttpRequest httpRequest) {
        Map<String, String> params = httpRequest.getParams();

        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
        logger.debug("User: {}", user);

        return "/index.html";
    }
}
