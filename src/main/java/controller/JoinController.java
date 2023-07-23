package controller;

import db.Database;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public enum JoinController implements HttpController {
    JOIN_CONTROLLER;

    private static final Logger logger = LoggerFactory.getLogger(JoinController.class);

    @Override
    public String process(Map<String, String> requestParams, HttpResponse response) {
        Database.addUser(new User(requestParams));
        for (User user : Database.findAll()) {
            logger.debug("{}", user);
        }
        return "redirect:/index.html";
    }

}
