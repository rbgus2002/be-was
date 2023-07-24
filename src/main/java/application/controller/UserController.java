package application.controller;

import application.model.User;
import db.Database;
import webserver.HttpMethod;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.annotation.RequestParameter;

@Controller
public class UserController {
    @RequestMapping(path = "/user/create", method = HttpMethod.GET)
    public String createUser(@RequestParameter(value = "userId") String userId,
                             @RequestParameter(value = "password") String password,
                             @RequestParameter(value = "name") String name,
                             @RequestParameter(value = "email") String email) {
        Database.addUser(new User(userId, password, name, email));
        return "redirect:/index.html";
    }
}
