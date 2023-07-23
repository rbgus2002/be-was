package controller;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

import java.util.UUID;

public enum LoginController implements HttpController {
    LOGIN_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        String userId = request.getParam("userId");
        String password = request.getParam("password");
        User user = Database.findUserById(userId);
        if (user != null && user.getPassword().equals(password)) {
            String sid = UUID.randomUUID().toString();
            response.setHeader("Set-Cookie", "sid=" + sid + "; Path=/");
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }
}
