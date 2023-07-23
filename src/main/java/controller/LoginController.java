package controller;

import db.Database;
import http.HttpResponse;
import model.User;

import java.util.Map;

public enum LoginController implements HttpController {
    LOGIN_CONTROLLER;

    @Override
    public String process(Map<String, String> requestParams, HttpResponse response) {
        String userId = requestParams.get("userId");
        String password = requestParams.get("password");
        User user = Database.findUserById(userId);
        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }
}
