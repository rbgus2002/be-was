package controller;

import annotation.RequestMapping;
import db.QuestionRepository;
import http.HttpRequest;
import http.HttpResponse;
import model.Question;

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
        Question question = Question.fromMap(request.getParams());
        QuestionRepository.addQuestion(question);
        return "redirect:/index.html";
    }
}
