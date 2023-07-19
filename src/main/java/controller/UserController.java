package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.HttpMethod;
import support.annotation.Controller;
import support.annotation.PathVariable;
import support.annotation.RequestMapping;

@Controller(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = HttpMethod.POST, value = "/create")
    public void create(@PathVariable("userId") String userId,
                       @PathVariable("password") String password,
                       @PathVariable("name") String name,
                       @PathVariable("email") String email) {

        logger.debug("유저 생성 요청");
        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }

}
