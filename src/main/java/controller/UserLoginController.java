package controller;

import annotation.RequestMapping;
import db.UserRepository;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;

@RequestMapping(values = {"/user/login", "/user/login.html"})
public class UserLoginController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if ("GET".equals(request.getMethod())) {
            return doGet(request);
        }
        if ("POST".equals(request.getMethod())) {
            return doPost(request, response);
        }
        response.setMethodNotAllowed();
        return "/error/405.html";
    }

    private String doGet(HttpRequest request) {
        if (request.hasValidSession()) {
            return "redirect:/index.html";
        }
        return "/user/login.html";
    }

    private String doPost(HttpRequest request, HttpResponse response) {
        String userId = request.getParam("userId");
        String password = request.getParam("password");
        User user = UserRepository.findUserById(userId);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = HttpSession.create();
            session.setAttribute("user", user);
            response.setSession(session);
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }
}
