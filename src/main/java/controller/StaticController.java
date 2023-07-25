package controller;

import http.HttpRequest;
import http.HttpResponse;

public class StaticController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        String url = request.getUrl();
        switch (url) {
            case "/":
                return "redirect:/index.html";
            case "/user/login.html":
                return "redirect:/user/login";
            case "/user/list.html":
                return "redirect:/user/list";
            case "/user/form.html":
                return "redirect:/user/form";
            default:
                return url;
        }
    }
}
