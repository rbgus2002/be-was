package controller;

import controller.annotaion.GetMapping;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class Controller {

    private final static Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping(path = "/user/create")
    public HttpResponse createUser(Map<String, String> queryString) throws IOException {
        User user = new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );
        Database.addUser(user);
        logger.debug("User: {}", user);
        return HttpResponse.redirect("/index.html");
    }
}
