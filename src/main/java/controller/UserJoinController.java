package controller;

import annotation.RequestMapping;
import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping(path = "/user/create")
public class UserJoinController implements HttpController {

    private static final Logger logger = LoggerFactory.getLogger(UserJoinController.class);

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        Database.addUser(new User(request.getParams()));
        for (User user : Database.findAll()) {
            logger.debug("{}", user);
        }
        return "redirect:/index.html";
    }

}
