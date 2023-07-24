package controller;

import db.Database;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;

public enum LoginController implements HttpController {
    LOGIN_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        String userId = request.getParam("userId");
        String password = request.getParam("password");
        User user = Database.findUserById(userId);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = HttpSession.create();
            session.addAttribute(user);
            response.setSession(session);
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }
}
