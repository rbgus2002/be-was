package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.HttpMethod;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import support.annotation.ResponseStatus;
import support.exception.FoundException;
import webserver.response.HttpStatus;

@Controller(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = HttpMethod.POST, value = "/login")
    @ResponseStatus(status = HttpStatus.FOUND, redirectionUrl = "/index.html")
    public void login(@RequestParam("userId") String userId,
                      @RequestParam("password") String password) throws FoundException {
        logger.debug("유저 로그인 요청");

        User user = Database.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            logger.debug("유저 없거나 비밀번호 불일치");
            throw new FoundException("/user/login_failed.html");
        }
        logger.debug("유저 로그인 성공~");

        // TODO set-Cookie

    }

    @RequestMapping(method = HttpMethod.POST, value = "/create")
    @ResponseStatus(status = HttpStatus.FOUND, redirectionUrl = "/index.html")
    public void create(@RequestParam("userId") String userId,
                       @RequestParam("password") String password,
                       @RequestParam("name") String name,
                       @RequestParam("email") String email) {

        logger.debug("유저 생성 요청");
        User user = new User(userId, password, name, email);
        Database.addUser(user);
    }

}
