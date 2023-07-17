package controller;

import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import db.Database;
import exception.ExceptionName;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.HttpMethod;
import webserver.RequestHandler;

@Controller(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @RequestMapping(method = HttpMethod.GET, value = "/create")
    public void create(@RequestParam("userId") String userId,
                       @RequestParam("password") String password,
                       @RequestParam(value = "name") String name,
                       @RequestParam(value = "email") String email) {

        logger.debug("유저 생성 요청");
        if (userId == null || password == null || name == null || email == null)
            throw new RuntimeException(ExceptionName.WRONG_ARGUMENT);
        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }

}
