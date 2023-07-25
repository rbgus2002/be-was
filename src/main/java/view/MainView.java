package view;

import model.User;

public class MainView {

    public static String changeToDynamic(User user) {
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>\r\n" +
                "<html lang=\"kr\">\r\n" +
                "\t<head>\r\n" +
                "\t\t<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n" +
                "\t\t<meta charset=\"utf-8\">\r\n" +
                "\t\t<title>SLiPP Java Web Programming</title>\r\n" +
                "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n" +
                "\t\t<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\r\n" +
                "\t\t<!--[if lt IE 9]>\r\n" +
                "\t\t\t<script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n" +
                "\t\t<![endif]-->\r\n" +
                "\t\t<link href=\"css/styles.css\" rel=\"stylesheet\">\r\n" +
                "\t</head>\r\n" +
                "\t\r\n" +
                "\t<body>\r\n" +
                "<nav class=\"navbar navbar-fixed-top header\">\r\n" +
                " \t<div class=\"col-md-12\">\r\n" +
                "        <div class=\"navbar-header\">\r\n" +
                "\r\n" +
                "            <a href=\"index.html\" class=\"navbar-brand\">SLiPP</a>\r\n" +
                "          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">\r\n" +
                "          <i class=\"glyphicon glyphicon-search\"></i>\r\n" +
                "          </button>\r\n" +
                "      \r\n" +
                "        </div>\r\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">\r\n" +
                "          <form class=\"navbar-form pull-left\">\r\n" +
                "              <div class=\"input-group\" style=\"max-width:470px;\">\r\n" +
                "                <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\r\n" +
                "                <div class=\"input-group-btn\">\r\n" +
                "                  <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\r\n" +
                "                </div>\r\n" +
                "              </div>\r\n" +
                "          </form>\r\n" +
                "          <ul class=\"nav navbar-nav navbar-right\">             \r\n" +
                "             <li>\r\n" +
                "                <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>\r\n" +
                "                <ul class=\"dropdown-menu\">\r\n" +
                "                  <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>\r\n" +
                "                  <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>\r\n" +
                "                </ul>\r\n" +
                "             </li>\r\n" +
                "             <li><a href=\"./user/list\"><i class=\"glyphicon glyphicon-user\"></i></a></li>\r\n" +
                "           </ul>\r\n" +
                "        </div>\t\r\n" +
                "     </div>\t\r\n" +
                "</nav>\r\n" +
                "<div class=\"navbar navbar-default\" id=\"subnav\">\r\n" +
                "    <div class=\"col-md-12\">\r\n" +
                "        <div class=\"navbar-header\">\r\n" +
                "            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>\r\n" +
                "            <ul class=\"nav dropdown-menu\">\r\n" +
                "                <li><a href=\"user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>\r\n" +
                "                <li class=\"nav-divider\"></li>\r\n" +
                "                <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>\r\n" +
                "            </ul>\r\n" +
                "            \r\n" +
                "            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">\r\n" +
                "            \t<span class=\"sr-only\">Toggle navigation</span>\r\n" +
                "            \t<span class=\"icon-bar\"></span>\r\n" +
                "            \t<span class=\"icon-bar\"></span>\r\n" +
                "            \t<span class=\"icon-bar\"></span>\r\n" +
                "            </button>            \r\n" +
                "        </div>\r\n" +
                "        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">\r\n" +
                "            <ul class=\"nav navbar-nav navbar-right\">\r\n" +
                "                <li class=\"active\"><a href=\"index.html\">Posts</a></li>\r\n" +
                "                <li><a href=\"#\" role=\"button\">" + user.getName() + " 님의 접속을 환영합니다.</a></li>\r\n" +
                "                <!--\r\n" +
                "                <li><a href=\"#loginModal\" role=\"button\" data-toggle=\"modal\">로그인</a></li>\r\n" +
                "                <li><a href=\"#registerModal\" role=\"button\" data-toggle=\"modal\">회원가입</a></li>\r\n" +
                "                -->\r\n" +
                "                <li><a href=\"#\" role=\"button\">로그아웃</a></li>\r\n" +
                "                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\r\n" +
                "            </ul>\r\n" +
                "        </div>\r\n" +
                "    </div>\r\n" +
                "</div>\r\n" +
                "\r\n" +
                "<div class=\"container\" id=\"main\">\r\n" +
                "   <div class=\"col-md-12 col-sm-12 col-lg-10 col-lg-offset-1\">\r\n" +
                "      <div class=\"panel panel-default qna-list\">\r\n" +
                "          <ul class=\"list\">\r\n" +
                "              <li>\r\n" +
                "                  <div class=\"wrap\">\r\n" +
                "                      <div class=\"main\">\r\n" +
                "                          <strong class=\"subject\">\r\n" +
                "                              <a href=\"./qna/show.html\">국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?</a>\r\n" +
                "                          </strong>\r\n" +
                "                          <div class=\"auth-info\">\r\n" +
                "                              <i class=\"icon-add-comment\"></i>\r\n" +
                "                              <span class=\"time\">2016-01-15 18:47</span>\r\n" +
                "                              <a href=\"./user/profile.html\" class=\"author\">자바지기</a>\r\n" +
                "                          </div>\r\n" +
                "                          <div class=\"reply\" title=\"댓글\">\r\n" +
                "                              <i class=\"icon-reply\"></i>\r\n" +
                "                              <span class=\"point\">8</span>\r\n" +
                "                          </div>\r\n" +
                "                      </div>\r\n" +
                "                  </div>\r\n" +
                "              </li>\r\n" +
                "              <li>\r\n" +
                "                  <div class=\"wrap\">\r\n" +
                "                      <div class=\"main\">\r\n" +
                "                          <strong class=\"subject\">\r\n" +
                "                              <a href=\"./qna/show.html\">runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?</a>\r\n" +
                "                          </strong>\r\n" +
                "                          <div class=\"auth-info\">\r\n" +
                "                              <i class=\"icon-add-comment\"></i>\r\n" +
                "                              <span class=\"time\">2016-01-05 18:47</span>\r\n" +
                "                              <a href=\"./user/profile.html\" class=\"author\">김문수</a>\r\n" +
                "                          </div>\r\n" +
                "                          <div class=\"reply\" title=\"댓글\">\r\n" +
                "                              <i class=\"icon-reply\"></i>\r\n" +
                "                              <span class=\"point\">12</span>\r\n" +
                "                          </div>\r\n" +
                "                      </div>\r\n" +
                "                  </div>\r\n" +
                "              </li>\r\n" +
                "          </ul>\r\n" +
                "          <div class=\"row\">\r\n" +
                "              <div class=\"col-md-3\"></div>\r\n" +
                "              <div class=\"col-md-6 text-center\">\r\n" +
                "                  <ul class=\"pagination center-block\" style=\"display:inline-block;\">\r\n" +
                "                      <li><a href=\"#\">«</a></li>\r\n" +
                "                      <li><a href=\"#\">1</a></li>\r\n" +
                "                      <li><a href=\"#\">2</a></li>\r\n" +
                "                      <li><a href=\"#\">3</a></li>\r\n" +
                "                      <li><a href=\"#\">4</a></li>\r\n" +
                "                      <li><a href=\"#\">5</a></li>\r\n" +
                "                      <li><a href=\"#\">»</a></li>\r\n" +
                "                </ul>\r\n" +
                "              </div>\r\n" +
                "              <div class=\"col-md-3 qna-write\">\r\n" +
                "                  <a href=\"./qna/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>\r\n" +
                "              </div>\r\n" +
                "          </div>\r\n" +
                "        </div>\r\n" +
                "    </div>\r\n" +
                "</div>\r\n" +
                "\r\n" +
                "<!-- script references -->\r\n" +
                "<script src=\"js/jquery-2.2.0.min.js\"></script>\r\n" +
                "<script src=\"js/bootstrap.min.js\"></script>\r\n" +
                "<script src=\"js/scripts.js\"></script>\r\n" +
                "\t</body>\r\n" +
                "</html>");

        return sb.toString();
    }
}
