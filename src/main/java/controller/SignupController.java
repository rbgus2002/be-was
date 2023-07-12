package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Map;

public class SignupController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);
    @Override
    public String execute(HttpRequest request, HttpResponse response) {

        logger.info("execute");

        Map<String, String> queries = request.getQuerys();
        User user = new User(queries.get("userId"), queries.get("password"), queries.get("name"), queries.get("email"));
        Database.addUser(user);
        logger.info("signup ok");
        return "redirect:/";
    }
}
