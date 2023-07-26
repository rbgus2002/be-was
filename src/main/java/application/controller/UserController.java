package application.controller;

import application.model.User;
import db.Database;
import webserver.HttpMethod;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.annotation.RequestParameter;

@Controller
public class UserController {
    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
    public String createUser(@RequestParameter(value = "userId") String userId,
                             @RequestParameter(value = "password") String password,
                             @RequestParameter(value = "name") String name,
                             @RequestParameter(value = "email") String email) {
        verifyCreateUser(userId);
        Database.addUser(new User(userId, password, name, email));
        return "redirect:/index.html";
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    public String loginUser(@RequestParameter(value = "userId") String userId,
                            @RequestParameter(value = "password") String password) {
        if (Database.authenticateUser(userId, password)) {
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }

    private void verifyCreateUser(String userId) {
        if (Database.hasUserId(userId)) {
            throw new IllegalArgumentException("이미 존재하는 userId 입니다.");
        }
    }
}
