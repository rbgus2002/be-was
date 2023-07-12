package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;

public class SignupController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
    @Override
    public String execute(HttpRequest request, HttpResponse response) {

        logger.info("execute");

        Map<String, String> querys = request.getQuerys();
        User user = new User(querys.get("userId"), querys.get("password"), querys.get("name"), querys.get("email"));
        Database.addUser(user);
        logger.info("signup ok");
        return "redirect:/";
    }
}
