package controller;

import annotation.RequestMapping;
import http.HttpRequest;
import http.HttpResponse;

@RequestMapping(values = {"/user/form", "/user/form.html"})
public class UserFormController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if ("GET".equals(request.getMethod())) {
            return doGet(request);
        }
        response.setMethodNotAllowed();
        return "/error/405.html";
    }

    private static String doGet(HttpRequest request) {
        if (request.hasValidSession()) {
            return "redirect:/index.html";
        }
        return "/user/form.html";
    }
}
