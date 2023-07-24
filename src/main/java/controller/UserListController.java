package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;

public enum UserListController implements HttpController {
    USER_LIST_CONTROLLER;

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        HttpSession session = request.getSession();
        if (session == null || !session.isValid()) {
            return "redirect:/index.html";
        }
        return "/user/list.html";
    }
}
