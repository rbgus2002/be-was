package service.html;

import model.User;

public class UserProfileHtmlService extends HtmlService {
    @Override
    public void renderContent(User sessionUser, StringBuilder htmlBuilder) {
        htmlBuilder.append("<div class=\"container\" id=\"main\">\n");
        htmlBuilder.append("<div class=\"col-md-6 col-md-offset-3\">\n");
        htmlBuilder.append("<div class=\"panel panel-default\">\n");
        htmlBuilder.append("<div class=\"panel-heading\"><h4>Profiles</h4></div>\n");
        htmlBuilder.append("<div class=\"panel-body\">\n");
        htmlBuilder.append("<div class=\"well well-sm\">\n");
        htmlBuilder.append("<div class=\"media\">\n");
        htmlBuilder.append("<a class=\"thumbnail pull-left\" href=\"#\">\n");
        htmlBuilder.append("<img class=\"media-object\" src=\"../images/80-text.png\">\n");
        htmlBuilder.append("</a>\n");
        htmlBuilder.append("<div class=\"media-body\">\n");
        htmlBuilder.append("<h4 class=\"media-heading\">" + sessionUser.getName() + "</h4>\n");
        htmlBuilder.append("<p>\n");
        htmlBuilder.append("<a href=\"#\" class=\"btn btn-xs btn-default\">" +
                "<span class=\"glyphicon glyphicon-envelope\"></span>&nbsp;" + sessionUser.getEmail() +"</a>\n");
        htmlBuilder.append("</p>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("<!-- script references -->\n");
        htmlBuilder.append("<script src=\"../js/jquery-2.2.0.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/bootstrap.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/scripts.js\"></script>\n");
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>");
    }
}
