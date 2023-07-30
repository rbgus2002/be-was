package application.presentation;

import application.common.StringUtils;
import application.model.Qna;
import application.model.User;
import application.service.QnaService;
import application.service.UserService;
import application.service.dto.QnaWriteRequest;
import common.annotation.Controller;
import common.annotation.HttpRequest;
import common.annotation.RequestBody;
import common.annotation.RequestMapping;
import common.annotation.RequestParam;
import java.util.Map;
import java.util.Optional;
import webserver.http.Http.Method;

@Controller
public class QnaController {
    private final QnaService qnaService;
    private final UserService userService;

    public QnaController() {
        this.qnaService = new QnaService();
        userService = new UserService();
    }

    @RequestMapping(value = "/qna/write", method = Method.POST)
    public String write(@RequestBody String body) {
        Map<String, String> map = StringUtils.extractBy(body);

        QnaWriteRequest qnaWriteRequest = new QnaWriteRequest(
                map.get("writer"),
                map.get("title"),
                map.get("contents")
        );

        qnaService.write(qnaWriteRequest);

        return "redirect:/index.html";
    }

    @RequestMapping(value = "/qna/show.html", method = Method.GET)
    public String show(@HttpRequest webserver.http.request.HttpRequest httpRequest, @RequestParam("id") String id) {
        Optional<Qna> optionalQna = qnaService.findBy(Integer.valueOf(id));
        Optional<String> authorizationUserId = httpRequest.getAuthorizationUserId();
        if (authorizationUserId.isEmpty()) {
            return "redirect:/user/login.html";
        }
        Optional<User> optionalUser = userService.findBy(authorizationUserId.get());
        if (optionalUser.isEmpty() || optionalQna.isEmpty()) {
            return "redirect:/user/login.html";
        }
        User user = optionalUser.get();
        Qna qna = optionalQna.get();

        return String.format("        <div class=\"panel panel-default\">\n"
                + "          <header class=\"qna-header\">\n"
                + "              <h2 class=\"qna-title\">%s</h2>\n"
                + "          </header>\n"
                + "          <div class=\"content-main\">\n"
                + "              <article class=\"article\">\n"
                + "                  <div class=\"article-header\">\n"
                + "                      <div class=\"article-header-thumb\">\n"
                + "                          <img src=\"https://graph.facebook.com/v2.3/100000059371774/picture\" class=\"article-author-thumb\" alt=\"\">\n"
                + "                      </div>\n"
                + "                      <div class=\"article-header-text\">\n"
                + "                          <a href=\"/users/92/kimmunsu\" class=\"article-author-name\">%s</a>\n"
                + "                          <a href=\"/questions/413\" class=\"article-header-time\" title=\"퍼머링크\">\n"
                + "                              %s\n"
                + "                              <i class=\"icon-link\"></i>\n"
                + "                          </a>\n"
                + "                      </div>\n"
                + "                  </div>\n"
                + "                  <div class=\"article-doc\">\n"
                + "                      %s"
                + "                  </div>\n"
                + "                    <br><br><br>"
                + "                  <div class=\"article-utils\">\n"
                + "                      <ul class=\"article-utils-list\">\n"
                + "                          <li>\n"
                + "                              <a class=\"link-modify-article\" href=\"/questions/423/form\">수정</a>\n"
                + "                          </li>\n"
                + "                          <li>\n"
                + "                              <form class=\"form-delete\" action=\"/questions/423\" method=\"POST\">\n"
                + "                                  <input type=\"hidden\" name=\"_method\" value=\"DELETE\">\n"
                + "                                  <button class=\"link-delete-article\" type=\"submit\">삭제</button>\n"
                + "                              </form>\n"
                + "                          </li>\n"
                + "                          <li>\n"
                + "                              <a class=\"link-modify-article\" href=\"/index.html\">목록</a>\n"
                + "                          </li>\n"
                + "                      </ul>\n"
                + "                  </div>\n"
                + "              </article>\n"
                + "        </div>", qna.getTitle(), user.getName(), qna.getLocalDateTime(), qna.getContents());
    }
}
