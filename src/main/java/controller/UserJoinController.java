package controller;

import annotation.RequestMapping;
import db.UserRepository;
import http.HttpRequest;
import http.HttpResponse;
import model.User;

@RequestMapping(values = {"/user/create"})
public class UserJoinController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if ("POST".equals(request.getMethod())) {
            return doPost(request);
        }
        response.setMethodNotAllowed();
        return "/error/405.html";
    }

    private static String doPost(HttpRequest request) {
        User user = User.fromMap(request.getParams());
        UserRepository.addUser(user);
        return "redirect:/index.html";
    }

}
