package application.controller;

import application.model.User;
import db.Database;
import webserver.HttpMethod;
import webserver.annotation.*;
import webserver.controller.CookieController;
import webserver.response.HttpResponseMessage;
import webserver.view.ViewData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
    public String createUser(@RequestParameter(value = "userId") String userId,
                             @RequestParameter(value = "password") String password,
                             @RequestParameter(value = "name") String name,
                             @RequestParameter(value = "email") String email) {

        if (isUserIdDuplicate(userId)) {
            return "redirect:/user/form_failed.html";
        }
        Database.addUser(new User(userId, password, name, email));
        return "redirect:/index.html";
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    public String loginUser(@RequestParameter(value = "userId") String userId,
                            @RequestParameter(value = "password") String password,
                            @HttpResponse HttpResponseMessage response) {
        if (Database.authenticateUser(userId, password)) {
            CookieController.createCookie(response, userId);
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }

    @RequestMapping(path = "/user/logout", method = HttpMethod.GET)
    public String logout(@HttpResponse HttpResponseMessage response,
                         @Cookies Map<String, String> cookies) {
        CookieController.deleteCookie(cookies, response);
        return "redirect:/index.html";
    }

    @RequestMapping(path = "/user/list.html", method = HttpMethod.GET)
    public ViewData showUserList() {
        Map<String, List<List<String>>> usersData = new HashMap<>();
        List<List<String>> userData = new ArrayList<>();
        List<String> userDatum;

        for (User user : Database.findAll()) {
            userDatum = new ArrayList<>();
            userDatum.add(user.getUserId());
            userDatum.add(user.getName());
            userDatum.add(user.getEmail());
            userData.add(userDatum);
        }
        usersData.put("user", userData);
        return ViewData.of("/user/list.html", usersData);
    }

    private boolean isUserIdDuplicate(String userId) {
        return Database.hasUserId(userId);
    }
}
