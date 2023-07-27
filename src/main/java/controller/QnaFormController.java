package controller;

import annotation.RequestMapping;
import db.QuestionRepository;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.Question;
import model.User;

@RequestMapping(values = {"/qna/form", "/qna/form.html"})
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Question question = new Question(user.getUserId(), request.getParam("title"), request.getParam("contents"));
        QuestionRepository.addQuestion(question);
        return "redirect:/index.html";
    }
}
