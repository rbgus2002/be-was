package controller;

import annotation.GetMapping;
import db.Database;
import model.User;
import model.dto.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping(value = "/user/create")
    public HttpResponse createUser(Map<String, String> query) throws IOException {
        logger.debug("GET user/create API START");

        User user = UserFactory.createUserFrom(query);
        Database.addUser(user);
        logger.debug("user 생성 : {}", Database.findUserById(query.get("userId")));

        return HttpResponse.redirect();
    }
}
