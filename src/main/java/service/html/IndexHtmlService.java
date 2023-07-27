package service.html;

import model.User;

public class IndexHtmlService extends HtmlService {
    @Override
    public void renderContent(User sessionUser, StringBuilder htmlBuilder) {
        htmlBuilder.append("<div class=\"container\" id=\"main\">\n");
        htmlBuilder.append("<div class=\"col-md-12 col-sm-12 col-lg-10 col-lg-offset-1\">\n");
        htmlBuilder.append("<div class=\"panel panel-default qna-list\">\n");
        htmlBuilder.append("<ul class=\"list\">\n");
        htmlBuilder.append("<li>\n");
        htmlBuilder.append("<div class=\"wrap\">\n");
        htmlBuilder.append("<div class=\"main\">\n");
        htmlBuilder.append("<strong class=\"subject\">\n");
        htmlBuilder.append("<a href=\"./qna/show.html\">국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?</a>\n");
        htmlBuilder.append("</strong>\n");
        htmlBuilder.append("<div class=\"auth-info\">\n");
        htmlBuilder.append("<i class=\"icon-add-comment\"></i>\n");
        htmlBuilder.append("<span class=\"time\">2016-01-15 18:47</span>\n");
        htmlBuilder.append("<a href=\"./user/profile.html\" class=\"author\">자바지기</a>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("<div class=\"reply\" title=\"댓글\">\n");
        htmlBuilder.append("<i class=\"icon-reply\"></i>\n");
        htmlBuilder.append("<span class=\"point\">8</span>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</li>\n");
        htmlBuilder.append("<li>\n");
        htmlBuilder.append("<div class=\"wrap\">\n");
        htmlBuilder.append("<div class=\"main\">\n");
        htmlBuilder.append("<strong class=\"subject\">\n");
        htmlBuilder.append("<a href=\"./qna/show.html\">runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?</a>\n");
        htmlBuilder.append("</strong>\n");
        htmlBuilder.append("<div class=\"auth-info\">\n");
        htmlBuilder.append("<i class=\"icon-add-comment\"></i>\n");
        htmlBuilder.append("<span class=\"time\">2016-01-05 18:47</span>\n");
        htmlBuilder.append("<a href=\"./user/profile.html\" class=\"author\">김문수</a>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("<div class=\"reply\" title=\"댓글\">\n");
        htmlBuilder.append("<i class=\"icon-reply\"></i>\n");
        htmlBuilder.append("<span class=\"point\">12</span>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</li>\n");
        htmlBuilder.append("</ul>\n");
        htmlBuilder.append("<div class=\"row\">\n");
        htmlBuilder.append("<div class=\"col-md-3\"></div>\n");
        htmlBuilder.append("<div class=\"col-md-6 text-center\">\n");
        htmlBuilder.append("<ul class=\"pagination center-block\" style=\"display:inline-block;\">\n");
        htmlBuilder.append("<li><a href=\"#\">«</a></li>\n");
        htmlBuilder.append("<li><a href=\"#\">1</a></li>\n");
        htmlBuilder.append("<li><a href=\"#\">2</a></li>\n");
        htmlBuilder.append("<li><a href=\"#\">3</a></li>\n");
        htmlBuilder.append("<li><a href=\"#\">4</a></li>\n");
        htmlBuilder.append("<li><a href=\"#\">5</a></li>\n");
        htmlBuilder.append("<li><a href=\"#\">»</a></li>\n");
        htmlBuilder.append("</ul>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("<div class=\"col-md-3 qna-write\">\n");
        htmlBuilder.append("<a href=\"./qna/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("<script src=\"js/jquery-2.2.0.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"js/bootstrap.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"js/scripts.js\"></script>\n");
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>\n");
    }
}
