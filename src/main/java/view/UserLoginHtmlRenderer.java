package view;

import model.User;

public class UserLoginHtmlRenderer extends HtmlRenderer {
    @Override
    public void renderMainContent(User sessionUser, StringBuilder htmlBuilder) {
        htmlBuilder.append("<div class=\"container\" id=\"main\">\n");
        htmlBuilder.append("<div class=\"col-md-6 col-md-offset-3\">\n");
        htmlBuilder.append("<div class=\"panel panel-default content-main\">\n");
        renderLoginForm(htmlBuilder);
        htmlBuilder.append("<script src=\"../js/jquery-2.2.0.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/bootstrap.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/scripts.js\"></script>\n");
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>\n");
    }

    static void renderLoginForm(StringBuilder htmlBuilder) {
        htmlBuilder.append("<form name=\"question\" method=\"post\" action=\"/user/login\">\n");
        htmlBuilder.append("<div class=\"form-group\">\n");
        htmlBuilder.append("<label for=\"userId\">사용자 아이디</label>\n");
        htmlBuilder.append("<input class=\"form-control\" id=\"userId\" name=\"userId\" placeholder=\"User ID\">\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("<div class=\"form-group\">\n");
        htmlBuilder.append("<label for=\"password\">비밀번호</label>\n");
        htmlBuilder.append("<input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" placeholder=\"Password\">\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("<button type=\"submit\" class=\"btn btn-success clearfix pull-right\">로그인</button>\n");
        htmlBuilder.append("<div class=\"clearfix\" />\n");
        htmlBuilder.append("</form>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
    }
}
