package controller;

import http.HttpRequest;
import http.HttpResponse;

public enum UserListController implements HttpController {
    USER_LIST_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if (!request.hasValidSession()) {
            return "redirect:/user/login.html";
        }
        return "/user/list.html";
    }
}
