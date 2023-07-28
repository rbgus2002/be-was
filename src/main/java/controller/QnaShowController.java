package controller;

import annotation.RequestMapping;
import db.QuestionRepository;
import http.HttpRequest;
import http.HttpResponse;
import model.Question;
import model.User;

@RequestMapping(values = {"/qna/show", "/qna/show.html"})
public class QnaShowController implements HttpController {

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        if ("GET".equals(request.getMethod())) {
            return doGet(request, response);
        }
        response.setMethodNotAllowed();
        return "/error/405.html";
    }

    private static String doGet(HttpRequest request, HttpResponse response) {
        if (!request.hasValidSession()) {
            return "redirect:/user/login";
        }
        response.setContentType("text/html");
        StringBuilder htmlBuilder = new StringBuilder();
        String index = request.getParam("index");
        Question question = QuestionRepository.findById(Integer.parseInt(index));
        if (question == null) {
            response.setNotFound();
            return "/error/404.html";
        }

        htmlBuilder.append("<!DOCTYPE html>\n");
        htmlBuilder.append("<html lang=\"kr\">\n");
        htmlBuilder.append("<head>\n");
        htmlBuilder.append("    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n");
        htmlBuilder.append("    <meta charset=\"utf-8\">\n");
        htmlBuilder.append("    <title>SLiPP Java Web Programming</title>\n");
        htmlBuilder.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n");
        htmlBuilder.append("    <link href=\"../css/bootstrap.min.css\" rel=\"stylesheet\">\n");
        htmlBuilder.append("    <!--[if lt IE 9]>\n");
        htmlBuilder.append("    <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n");
        htmlBuilder.append("    <![endif]-->\n");
        htmlBuilder.append("    <link href=\"../css/styles.css\" rel=\"stylesheet\">\n");
        htmlBuilder.append("</head>\n");
        htmlBuilder.append("<body>\n");
        htmlBuilder.append("<nav class=\"navbar navbar-fixed-top header\">\n");
        htmlBuilder.append("    <div class=\"col-md-12\">\n");
        htmlBuilder.append("        <div class=\"navbar-header\">\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("            <a href=\"../index.html\" class=\"navbar-brand\">SLiPP</a>\n");
        htmlBuilder.append("            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">\n");
        htmlBuilder.append("                <i class=\"glyphicon glyphicon-search\"></i>\n");
        htmlBuilder.append("            </button>\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("        </div>\n");
        htmlBuilder.append("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">\n");
        htmlBuilder.append("            <form class=\"navbar-form pull-left\">\n");
        htmlBuilder.append("                <div class=\"input-group\" style=\"max-width:470px;\">\n");
        htmlBuilder.append("                    <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\n");
        htmlBuilder.append("                    <div class=\"input-group-btn\">\n");
        htmlBuilder.append("                        <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\n");
        htmlBuilder.append("                    </div>\n");
        htmlBuilder.append("                </div>\n");
        htmlBuilder.append("            </form>\n");
        htmlBuilder.append("            <ul class=\"nav navbar-nav navbar-right\">\n");
        htmlBuilder.append("                <li>\n");
        htmlBuilder.append("                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>\n");
        htmlBuilder.append("                    <ul class=\"dropdown-menu\">\n");
        htmlBuilder.append("                        <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>\n");
        htmlBuilder.append("                        <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>\n");
        htmlBuilder.append("                    </ul>\n");
        htmlBuilder.append("                </li>\n");
        htmlBuilder.append("                <li><a href=\"../user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li>\n");
        htmlBuilder.append("            </ul>\n");
        htmlBuilder.append("        </div>\n");
        htmlBuilder.append("    </div>\n");
        htmlBuilder.append("</nav>\n");
        htmlBuilder.append("<div class=\"navbar navbar-default\" id=\"subnav\">\n");
        htmlBuilder.append("    <div class=\"col-md-12\">\n");
        htmlBuilder.append("        <div class=\"navbar-header\">\n");
        htmlBuilder.append("            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>\n");
        htmlBuilder.append("            <ul class=\"nav dropdown-menu\">\n");
        htmlBuilder.append("                <li><a href=\"../user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>\n");
        htmlBuilder.append("                <li class=\"nav-divider\"></li>\n");
        htmlBuilder.append("                <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>\n");
        htmlBuilder.append("            </ul>\n");
        htmlBuilder.append("            \n");
        htmlBuilder.append("            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">\n");
        htmlBuilder.append("            \t<span class=\"sr-only\">Toggle navigation</span>\n");
        htmlBuilder.append("            \t<span class=\"icon-bar\"></span>\n");
        htmlBuilder.append("            \t<span class=\"icon-bar\"></span>\n");
        htmlBuilder.append("            \t<span class=\"icon-bar\"></span>\n");
        htmlBuilder.append("            </button>            \n");
        htmlBuilder.append("        </div>\n");
        htmlBuilder.append("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">\n");
        htmlBuilder.append("            <ul class=\"nav navbar-nav navbar-right\">\n");
        if (request.hasValidSession()) {
            User user = (User) request.getSession().getAttribute("user");
            htmlBuilder.append("<li><p class=\"navbar-text\">" + user.getName() + "님 환영합니다.</p></li>");
            htmlBuilder.append("                <li class=\"active\"><a href=\"../index.html\">Posts</a></li>\n");
            htmlBuilder.append("                <li><a href=\"../user/logout\" role=\"button\">로그아웃</a></li>\n");
            htmlBuilder.append("                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\n");
        } else {
            htmlBuilder.append("                <li class=\"active\"><a href=\"index.html\">Posts</a></li>\n");
            htmlBuilder.append("                <li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>\n");
            htmlBuilder.append("                <li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>\n");
        }
        htmlBuilder.append("            </ul>\n");
        htmlBuilder.append("        </div>\n");
        htmlBuilder.append("    </div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("<div class=\"container\" id=\"main\">\n");
        htmlBuilder.append("    <div class=\"col-md-12 col-sm-12 col-lg-12\">\n");
        htmlBuilder.append("        <div class=\"panel panel-default\">\n");
        htmlBuilder.append("          <header class=\"qna-header\">\n");
        htmlBuilder.append("              <h2 class=\"qna-title\">" + question.getTitle() + "</h2>\n");
        htmlBuilder.append("          </header>\n");
        htmlBuilder.append("          <div class=\"content-main\">\n");
        htmlBuilder.append("              <article class=\"article\">\n");
        htmlBuilder.append("                  <div class=\"article-header\">\n");
        htmlBuilder.append("                      <div class=\"article-header-thumb\">\n");
        htmlBuilder.append("                          <img src=\"https://graph.facebook.com/v2.3/100000059371774/picture\" class=\"article-author-thumb\" alt=\"\">\n");
        htmlBuilder.append("                      </div>\n");
        htmlBuilder.append("                      <div class=\"article-header-text\">\n");
        htmlBuilder.append("                          <a href=\"/users/92/kimmunsu\" class=\"article-author-name\">" + question.getWriter() + "</a>\n");
        htmlBuilder.append("                          <a href=\"/questions/413\" class=\"article-header-time\" title=\"퍼머링크\">\n");
        htmlBuilder.append("                              2015-12-30 01:47\n");
        htmlBuilder.append("                              <i class=\"icon-link\"></i>\n");
        htmlBuilder.append("                          </a>\n");
        htmlBuilder.append("                      </div>\n");
        htmlBuilder.append("                  </div>\n");
        htmlBuilder.append("                  <div class=\"article-doc\">\n");
        htmlBuilder.append("                      <p>" + question.getContents() + "</p>\n");
        htmlBuilder.append("                  </div>\n");
        htmlBuilder.append("                  <div class=\"article-utils\">\n");
        htmlBuilder.append("                      <ul class=\"article-utils-list\">\n");
        htmlBuilder.append("                          <li>\n");
        htmlBuilder.append("                              <a class=\"link-modify-article\" href=\"/questions/423/form\">수정</a>\n");
        htmlBuilder.append("                          </li>\n");
        htmlBuilder.append("                          <li>\n");
        htmlBuilder.append("                              <form class=\"form-delete\" action=\"/questions/423\" method=\"POST\">\n");
        htmlBuilder.append("                                  <input type=\"hidden\" name=\"_method\" value=\"DELETE\">\n");
        htmlBuilder.append("                                  <a class=\"link-delete-article\" type=\"submit\">삭제</a>\n");
        htmlBuilder.append("                              </form>\n");
        htmlBuilder.append("                          </li>\n");
        htmlBuilder.append("                          <li>\n");
        htmlBuilder.append("                              <a class=\"link-modify-article\" href=\"/index.html\">목록</a>\n");
        htmlBuilder.append("                          </li>\n");
        htmlBuilder.append("                      </ul>\n");
        htmlBuilder.append("                  </div>\n");
        htmlBuilder.append("              </article>\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("              <div class=\"qna-comment\">\n");
        htmlBuilder.append("                  <div class=\"qna-comment-slipp\">\n");
        htmlBuilder.append("                      <p class=\"qna-comment-count\"><strong>0</strong>개의 의견</p>\n");
        htmlBuilder.append("                      <div class=\"qna-comment-slipp-articles\">\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("                          <form class=\"answer-form\">\n");
        htmlBuilder.append("                              <div class=\"form-group\" style=\"padding:14px;\">\n");
        htmlBuilder.append("                                  <textarea class=\"form-control\" placeholder=\"Update your status\"></textarea>\n");
        htmlBuilder.append("                              </div>\n");
        htmlBuilder.append("                              <button class=\"btn btn-success pull-right\" type=\"button\">답변하기</button>\n");
        htmlBuilder.append("                              <div class=\"clearfix\" />\n");
        htmlBuilder.append("                          </form>\n");
        htmlBuilder.append("                      </div>\n");
        htmlBuilder.append("                  </div>\n");
        htmlBuilder.append("              </div>\n");
        htmlBuilder.append("          </div>\n");
        htmlBuilder.append("        </div>\n");
        htmlBuilder.append("    </div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("<script type=\"text/template\" id=\"answerTemplate\">\n");
        htmlBuilder.append("\t<article class=\"article\">\n");
        htmlBuilder.append("\t\t<div class=\"article-header\">\n");
        htmlBuilder.append("\t\t\t<div class=\"article-header-thumb\">\n");
        htmlBuilder.append("\t\t\t\t<img src=\"https://graph.facebook.com/v2.3/1324855987/picture\" class=\"article-author-thumb\" alt=\"\">\n");
        htmlBuilder.append("\t\t\t</div>\n");
        htmlBuilder.append("\t\t\t<div class=\"article-header-text\">\n");
        htmlBuilder.append("\t\t\t\t<a href=\"#\" class=\"article-author-name\">{0}</a>\n");
        htmlBuilder.append("\t\t\t\t<div class=\"article-header-time\">{1}</div>\n");
        htmlBuilder.append("\t\t\t</div>\n");
        htmlBuilder.append("\t\t</div>\n");
        htmlBuilder.append("\t\t<div class=\"article-doc comment-doc\">\n");
        htmlBuilder.append("\t\t\t{2}\n");
        htmlBuilder.append("\t\t</div>\n");
        htmlBuilder.append("\t\t<div class=\"article-utils\">\n");
        htmlBuilder.append("\t\t<ul class=\"article-utils-list\">\n");
        htmlBuilder.append("\t\t\t<li>\n");
        htmlBuilder.append("\t\t\t\t<a class=\"link-modify-article\" href=\"/api/questions/{3}/answers/{4}/form\">수정</a>\t\t\t\t\n");
        htmlBuilder.append("\t\t\t</li>\n");
        htmlBuilder.append("\t\t\t<li>\n");
        htmlBuilder.append("\t\t\t\t<form class=\"delete-answer-form\" action=\"/api/questions/{3}/answers/{4}\" method=\"POST\">\n");
        htmlBuilder.append("\t\t\t\t\t<input type=\"hidden\" name=\"_method\" value=\"DELETE\">\n");
        htmlBuilder.append("                     <a type=\"submit\" class=\"delete-answer-button\">삭제</a>\n");
        htmlBuilder.append("\t\t\t\t</form>\n");
        htmlBuilder.append("\t\t\t</li>\n");
        htmlBuilder.append("\t\t</ul>\n");
        htmlBuilder.append("\t\t</div>\n");
        htmlBuilder.append("\t</article>\n");
        htmlBuilder.append("</script>\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("<!-- script references -->\n");
        htmlBuilder.append("<script src=\"../js/jquery-2.2.0.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/bootstrap.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/scripts.js\"></script>\n");
        htmlBuilder.append("\t</body>\n");
        htmlBuilder.append("</html>");

        response.setBody(htmlBuilder.toString().getBytes());
        return null;
    }
}
