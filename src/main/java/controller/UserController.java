package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.HttpMethod;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;

@Controller(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = HttpMethod.GET, value = "/create")
    public void create(@RequestParam("userId") String userId,
                       @RequestParam("password") String password,
                       @RequestParam("name") String name,
                       @RequestParam("email") String email) {

        logger.debug("유저 생성 요청");
        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }

}
