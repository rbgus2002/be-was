package controller;

import annotation.RequestMapping;
import http.HttpRequest;
import http.HttpResponse;

@RequestMapping(path = "/qna/form")
public class QnaFormController implements HttpController {
    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if (!request.hasValidSession()) {
            return "redirect:/user/login";
        }
        if ("POST".equals(request.getMethod())) {
            return doPost(request, response);
        }
        return doGet(request, response);
    }

    private String doGet(HttpRequest request, HttpResponse response) {
        return "/qna/form.html";
    }

    private String doPost(HttpRequest request, HttpResponse response) {


        return "redirect:/index.html";
    }
}
