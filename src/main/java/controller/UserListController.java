package controller;

import http.HttpRequest;
import http.HttpResponse;

public class UserListController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if (!request.hasValidSession()) {
            return "redirect:/user/login.html";
        }
        return "/user/list.html";
    }
}
