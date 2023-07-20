package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

public enum JoinController implements HttpController {
    JOIN_CONTROLLER;

    private static final Logger logger = LoggerFactory.getLogger(JoinController.class);

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        Database.addUser(new User(request.getParams()));
        for (User user : Database.findAll()) {
            logger.debug("{}", user);
        }
        return "redirect:/index.html";
    }

}
