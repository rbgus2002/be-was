package view;

import model.User;

public class UserLoginFailedHtmlRenderer extends HtmlRenderer {
    @Override
    public void renderMainContent(User sessionUser, StringBuilder htmlBuilder) {
        htmlBuilder.append("<div class=\"container\" id=\"main\">\n");
        htmlBuilder.append("<div class=\"col-md-6 col-md-offset-3\">\n");
        htmlBuilder.append("<div class=\"panel panel-default content-main\">\n");
        htmlBuilder.append("<div class=\"alert alert-danger\" role=\"alert\">아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.</div>\n");
        UserLoginHtmlRenderer.renderLoginForm(htmlBuilder);
        htmlBuilder.append("\n");
        htmlBuilder.append("<!-- script references -->\n");
        htmlBuilder.append("<script src=\"../js/jquery-2.2.0.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/bootstrap.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/scripts.js\"></script>\n");
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>");
    }
}
