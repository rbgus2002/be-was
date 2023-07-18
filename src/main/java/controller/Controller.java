package controller;

import annotation.GetMapping;
import db.Database;
import model.User;
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

        // TODO : Service layer 분리
        String userId = query.get("userId");
        String password = query.get("password");
        String name = query.get("name");
        String email = query.get("email");

        User user = new User(userId, password, name, email);
        Database.addUser(user);

        logger.debug("user 생성 : {}", Database.findUserById(userId));

        return HttpResponse.redirect();
    }
}
