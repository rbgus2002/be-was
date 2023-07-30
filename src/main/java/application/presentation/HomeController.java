package application.presentation;

import application.model.Qna;
import application.model.User;
import application.service.QnaService;
import application.service.UserService;
import common.annotation.Controller;
import common.annotation.HttpRequest;
import common.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import webserver.http.Http.Method;

@Controller
public class HomeController {
    private final UserService userService;
    private final QnaService qnaService;

    public HomeController() {
        this.userService = new UserService();
        this.qnaService = new QnaService();
    }

    @RequestMapping(value = "/index.html", method = Method.GET)
    public String[] home(@HttpRequest webserver.http.request.HttpRequest httpRequest) {
        String[] result = {loginText(httpRequest), qnaText(qnaService.findAll())};
        return result;
    }

    private String qnaText(final List<Qna> qnas) {
        StringBuilder result = new StringBuilder();
        result.append("<ul class=\"list\">\n");
        for (Qna qna : qnas) {
            StringBuilder temp = new StringBuilder();
            temp.append("<li>\n");
            temp.append("<div class=\"wrap\">\n");
            temp.append("<div class=\"main\">\n");
            temp.append("<strong class=\"subject\">\n");
            temp.append(String.format("<a href=\"./qna/show.html?id=%s\">%s</a>\n", qna.getId(), qna.getTitle()));
            temp.append("</strong>\n");
            temp.append("<div class=\"auth-info\">\n");
            temp.append("<i class=\"icon-add-comment\"></i>\n");
            temp.append(String.format("<span class=\"time\">%s</span>\n", qna.getLocalDateTime()));
            temp.append(String.format("<a href=\"./user/profile.html\" class=\"author\">%s</a>\n", qna.getWriter()));
            temp.append("</div>\n");
            temp.append("<div class=\"reply\" title=\"댓글\">\n");
            temp.append("<i class=\"icon-reply\"></i>\n");
            temp.append("<span class=\"point\">8</span>\n");
            temp.append("</div>\n");
            temp.append("</div>\n");
            temp.append("</div>\n");
            temp.append("</li>\n");

            result.append(temp);
        }
        result.append("</ul>\n");
        return result.toString();
    }

    private String loginText(final webserver.http.request.HttpRequest httpRequest) {
        Optional<String> authorizationUserId = httpRequest.getAuthorizationUserId();
        if (authorizationUserId.isEmpty()) {
            return "<a href=\"user/login.html\" role=\"button\">로그인</a>";
        }
        Optional<User> optionalUser = userService.findBy(authorizationUserId.get());
        if (optionalUser.isEmpty()) {
            return "<a href=\"user/login.html\" role=\"button\">로그인</a>";
        }
        return "<a href=\"#\" role=\"button\">" + optionalUser.get().getName() + "</a>";
    }
}
