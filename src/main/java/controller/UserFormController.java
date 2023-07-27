package controller;

import annotation.RequestMapping;
import http.HttpRequest;
import http.HttpResponse;

@RequestMapping(values = {"/user/form", "/user/form.html"})
public class UserFormController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if (request.hasValidSession()) {
            return "redirect:/index.html";
        }
        return "/user/form.html";
    }
}
