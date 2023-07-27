package controller;

import annotation.RequestMapping;
import http.HttpRequest;
import http.HttpResponse;

@RequestMapping(path = "/user/form")
public class FormController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if (request.hasValidSession()) {
            return "redirect:/index.html";
        }
        return "/user/form.html";
    }
}
