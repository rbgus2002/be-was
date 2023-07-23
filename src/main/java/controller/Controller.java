package controller;

import annotation.GetMapping;
import annotation.PostMapping;
import db.Database;
import model.User;
import model.factory.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @PostMapping(value = "/user/create")
    public String createUser(Map<String, String> body){
        logger.debug("POST user/create API START");

        User user = UserFactory.createUserFrom(body);
        Database.addUser(user);
        logger.debug("user 생성 : {}", Database.findUserById(body.get("userId")));

        return "/index.html";
    }
}
