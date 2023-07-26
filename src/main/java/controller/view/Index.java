package controller.view;

import controller.Controller;
import model.User;
import webserver.reponse.HttpResponse;
import webserver.reponse.HttpResponseStatus;
import webserver.reponse.Type;
import webserver.request.HttpRequest;
import webserver.session.UserSessionManager;

public class Index implements Controller {

    private static Index index;

    private Index() {

    }

    public static Index getInstance() {
        if(index == null) {
            index = new Index();
        }
        return index;
    }

    @Override
    public void execute(HttpRequest request, HttpResponse response) {
        User user = UserSessionManager.getInstance().getSession(request);
        response.setStatus(HttpResponseStatus.STATUS_200);
        response.setBodyByFile(getHtml(user), Type.HTML);
    }

    private byte[] getHtml(User user) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html>\r\n")
                .append("<html lang=\"kr\">\r\n")
                .append("\t<head>\r\n")
                .append("\t\t<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n")
                .append("\t\t<meta charset=\"utf-8\">\r\n")
                .append("\t\t<title>SLiPP Java Web Programming</title>\r\n")
                .append("\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n")
                .append("\t\t<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\r\n")
                .append("\t\t<!--[if lt IE 9]>\r\n")
                .append("\t\t\t<script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n")
                .append("\t\t<![endif]-->\r\n")
                .append("\t\t<link href=\"css/styles.css\" rel=\"stylesheet\">\r\n")
                .append("\t</head>\r\n")
                .append("\t\r\n")
                .append("\t<body>\r\n")
                .append("<nav class=\"navbar navbar-fixed-top header\">\r\n")
                .append(" \t<div class=\"col-md-12\">\r\n")
                .append("        <div class=\"navbar-header\">\r\n")
                .append("\r\n")
                .append("            <a href=\"index.html\" class=\"navbar-brand\">SLiPP</a>\r\n")
                .append(
                        "          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">\r\n")
                .append("          <i class=\"glyphicon glyphicon-search\"></i>\r\n")
                .append("          </button>\r\n")
                .append("      \r\n")
                .append("        </div>\r\n")
                .append("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">\r\n")
                .append("          <form class=\"navbar-form pull-left\">\r\n")
                .append("              <div class=\"input-group\" style=\"max-width:470px;\">\r\n")
                .append(
                        "                <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\r\n")
                .append("                <div class=\"input-group-btn\">\r\n")
                .append(
                        "                  <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\r\n")
                .append("                </div>\r\n")
                .append("              </div>\r\n")
                .append("          </form>\r\n")
                .append("          <ul class=\"nav navbar-nav navbar-right\">             \r\n")
                .append("             <li>\r\n")
                .append(
                        "                <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>\r\n")
                .append("                <ul class=\"dropdown-menu\">\r\n")
                .append("                  <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>\r\n")
                .append("                  <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>\r\n")
                .append("                </ul>\r\n")
                .append("             </li>\r\n")
                .append(
                        "             <li><a href=\"./user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li>\r\n")
                .append("           </ul>\r\n")
                .append("        </div>\t\r\n")
                .append("     </div>\t\r\n")
                .append("</nav>\r\n")
                .append("<div class=\"navbar navbar-default\" id=\"subnav\">\r\n")
                .append("    <div class=\"col-md-12\">\r\n")
                .append("        <div class=\"navbar-header\">\r\n")
                .append(
                        "            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>\r\n")
                .append("            <ul class=\"nav dropdown-menu\">\r\n")
                .append(
                        "                <li><a href=\"user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>\r\n")
                .append("                <li class=\"nav-divider\"></li>\r\n")
                .append(
                        "                <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>\r\n")
                .append("            </ul>\r\n")
                .append("            \r\n")
                .append(
                        "            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">\r\n")
                .append("            \t<span class=\"sr-only\">Toggle navigation</span>\r\n")
                .append("            \t<span class=\"icon-bar\"></span>\r\n")
                .append("            \t<span class=\"icon-bar\"></span>\r\n")
                .append("            \t<span class=\"icon-bar\"></span>\r\n")
                .append("            </button>            \r\n")
                .append("        </div>\r\n")
                .append("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">\r\n")
                .append("            <ul class=\"nav navbar-nav navbar-right\">\r\n");

        if (user == null) {
            htmlBuilder.append("                <li class=\"active\"><a href=\"index.html\">Posts</a></li>\r\n")
                    .append("                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>\r\n");
        } else {
            htmlBuilder
                    .append("                <li class=\"active\"><a href=\"index.html\">Posts</a></li>\r\n")
                    .append("                <li><a role=\"button\">")
                    .append(user.getName())
                    .append("</a></li>\r\n");
        }

        htmlBuilder.append("                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>\r\n")
                .append("                <!--\r\n")
                .append(
                        "                <li><a href=\"#loginModal\" role=\"button\" data-toggle=\"modal\">로그인</a></li>\r\n")
                .append(
                        "                <li><a href=\"#registerModal\" role=\"button\" data-toggle=\"modal\">회원가입</a></li>\r\n")
                .append("                -->\r\n")
                .append("                <li><a href=\"#\" role=\"button\">로그아웃</a></li>\r\n")
                .append("                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\r\n")
                .append("            </ul>\r\n")
                .append("        </div>\r\n")
                .append("    </div>\r\n")
                .append("</div>\r\n")
                .append("\r\n")
                .append("<div class=\"container\" id=\"main\">\r\n")
                .append("   <div class=\"col-md-12 col-sm-12 col-lg-10 col-lg-offset-1\">\r\n")
                .append("      <div class=\"panel panel-default qna-list\">\r\n")
                .append("          <ul class=\"list\">\r\n")
                .append("              <li>\r\n")
                .append("                  <div class=\"wrap\">\r\n")
                .append("                      <div class=\"main\">\r\n")
                .append("                          <strong class=\"subject\">\r\n")
                .append(
                        "                              <a href=\"./qna/show.html\">국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?</a>\r\n")
                .append("                          </strong>\r\n")
                .append("                          <div class=\"auth-info\">\r\n")
                .append("                              <i class=\"icon-add-comment\"></i>\r\n")
                .append("                              <span class=\"time\">2016-01-15 18:47</span>\r\n")
                .append("                              <a href=\"./user/profile.html\" class=\"author\">자바지기</a>\r\n")
                .append("                          </div>\r\n")
                .append("                          <div class=\"reply\" title=\"댓글\">\r\n")
                .append("                              <i class=\"icon-reply\"></i>\r\n")
                .append("                              <span class=\"point\">8</span>\r\n")
                .append("                          </div>\r\n")
                .append("                      </div>\r\n")
                .append("                  </div>\r\n")
                .append("              </li>\r\n")
                .append("              <li>\r\n")
                .append("                  <div class=\"wrap\">\r\n")
                .append("                      <div class=\"main\">\r\n")
                .append("                          <strong class=\"subject\">\r\n")
                .append(
                        "                              <a href=\"./qna/show.html\">runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?</a>\r\n")
                .append("                          </strong>\r\n")
                .append("                          <div class=\"auth-info\">\r\n")
                .append("                              <i class=\"icon-add-comment\"></i>\r\n")
                .append("                              <span class=\"time\">2016-01-05 18:47</span>\r\n")
                .append("                              <a href=\"./user/profile.html\" class=\"author\">김문수</a>\r\n")
                .append("                          </div>\r\n")
                .append("                          <div class=\"reply\" title=\"댓글\">\r\n")
                .append("                              <i class=\"icon-reply\"></i>\r\n")
                .append("                              <span class=\"point\">12</span>\r\n")
                .append("                          </div>\r\n")
                .append("                      </div>\r\n")
                .append("                  </div>\r\n")
                .append("              </li>\r\n")
                .append("          </ul>\r\n")
                .append("          <div class=\"row\">\r\n")
                .append("              <div class=\"col-md-3\"></div>\r\n")
                .append("              <div class=\"col-md-6 text-center\">\r\n")
                .append("                  <ul class=\"pagination center-block\" style=\"display:inline-block;\">\r\n")
                .append("                      <li><a href=\"#\">«</a></li>\r\n")
                .append("                      <li><a href=\"#\">1</a></li>\r\n")
                .append("                      <li><a href=\"#\">2</a></li>\r\n")
                .append("                      <li><a href=\"#\">3</a></li>\r\n")
                .append("                      <li><a href=\"#\">4</a></li>\r\n")
                .append("                      <li><a href=\"#\">5</a></li>\r\n")
                .append("                      <li><a href=\"#\">»</a></li>\r\n")
                .append("                </ul>\r\n")
                .append("              </div>\r\n")
                .append("              <div class=\"col-md-3 qna-write\">\r\n")
                .append(
                        "                  <a href=\"./qna/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>\r\n")
                .append("              </div>\r\n")
                .append("          </div>\r\n")
                .append("        </div>\r\n")
                .append("    </div>\r\n")
                .append("</div>\r\n")
                .append("\r\n")
                .append("<!-- script references -->\r\n")
                .append("<script src=\"js/jquery-2.2.0.min.js\"></script>\r\n")
                .append("<script src=\"js/bootstrap.min.js\"></script>\r\n")
                .append("<script src=\"js/scripts.js\"></script>\r\n")
                .append("\t</body>\r\n")
                .append("</html>");

        return htmlBuilder.toString().getBytes();
    }

}
