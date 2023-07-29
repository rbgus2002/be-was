package view;

import dto.UserResponseDto;

import java.util.Map;

public class IndexServlet implements Servlet {
    @Override
    public byte[] doGet(Map<String, Object> data) {
        UserResponseDto userResponseDto = (UserResponseDto) data.get("users");

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<!DOCTYPE html>\n");
        stringBuilder.append("<html lang=\"kr\">\n");
        stringBuilder.append("    <head>\n");
        stringBuilder.append("        <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n");
        stringBuilder.append("        <meta charset=\"utf-8\">\n");
        stringBuilder.append("        <title>SLiPP Java Web Programming</title>\n");
        stringBuilder.append("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n");
        stringBuilder.append("        <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n");
        stringBuilder.append("        <!--[if lt IE 9]>\n");
        stringBuilder.append("            <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n");
        stringBuilder.append("        <![endif]-->\n");
        stringBuilder.append("        <link href=\"css/styles.css\" rel=\"stylesheet\">\n");
        stringBuilder.append("    </head>\n");
        stringBuilder.append("    <body>\n");
        stringBuilder.append("        <nav class=\"navbar navbar-fixed-top header\">\n");
        stringBuilder.append("            <div class=\"col-md-12\">\n");
        stringBuilder.append("                <div class=\"navbar-header\">\n");
        stringBuilder.append("                    <a href=\"index.html\" class=\"navbar-brand\">SLiPP</a>\n");
        stringBuilder.append("                    <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">\n");
        stringBuilder.append("                        <i class=\"glyphicon glyphicon-search\"></i>\n");
        stringBuilder.append("                    </button>\n");
        stringBuilder.append("                </div>\n");
        stringBuilder.append("                <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">\n");
        stringBuilder.append("                    <form class=\"navbar-form pull-left\">\n");
        stringBuilder.append("                        <div class=\"input-group\" style=\"max-width:470px;\">\n");
        stringBuilder.append("                            <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\n");
        stringBuilder.append("                            <div class=\"input-group-btn\">\n");
        stringBuilder.append("                                <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\n");
        stringBuilder.append("                            </div>\n");
        stringBuilder.append("                        </div>\n");
        stringBuilder.append("                    </form>\n");
        stringBuilder.append("                    <ul class=\"nav navbar-nav navbar-right\">\n");
        stringBuilder.append("                        <li>\n");
        stringBuilder.append("                            <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>\n");
        stringBuilder.append("                            <ul class=\"dropdown-menu\">\n");
        stringBuilder.append("                                <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>\n");
        stringBuilder.append("                                <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>\n");
        stringBuilder.append("                            </ul>\n");
        stringBuilder.append("                        </li>\n");
        stringBuilder.append("                        <li><a href=\"./user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li>\n");
        stringBuilder.append("                    </ul>\n");
        stringBuilder.append("                </div>\n");
        stringBuilder.append("            </div>\n");
        stringBuilder.append("        </nav>\n");
        stringBuilder.append("        <div class=\"navbar navbar-default\" id=\"subnav\">\n");
        stringBuilder.append("            <div class=\"col-md-12\">\n");
        stringBuilder.append("                <div class=\"navbar-header\">\n");
        stringBuilder.append("                    <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>\n");
        stringBuilder.append("                    <ul class=\"nav dropdown-menu\">\n");
        stringBuilder.append("                        <li><a href=\"user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>\n");
        stringBuilder.append("                        <li class=\"nav-divider\"></li>\n");
        stringBuilder.append("                        <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>\n");
        stringBuilder.append("                    </ul>\n");
        stringBuilder.append("                    <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">\n");
        stringBuilder.append("                        <span class=\"sr-only\">Toggle navigation</span>\n");
        stringBuilder.append("                        <span class=\"icon-bar\"></span>\n");
        stringBuilder.append("                        <span class=\"icon-bar\"></span>\n");
        stringBuilder.append("                        <span class=\"icon-bar\"></span>\n");
        stringBuilder.append("                    </button>\n");
        stringBuilder.append("                </div>\n");
        stringBuilder.append("                <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">\n");
        stringBuilder.append("                    <ul class=\"nav navbar-nav navbar-right\">\n");
        stringBuilder.append("                        <li class=\"active\"><a href=\"index.html\">Posts</a></li>\n");

        makeLoginForm(userResponseDto, stringBuilder);

        stringBuilder.append("                    </ul>\n");
        stringBuilder.append("                </div>\n");
        stringBuilder.append("            </div>\n");
        stringBuilder.append("        </div>\n");
        stringBuilder.append("        <div class=\"container\" id=\"main\">\n");
        stringBuilder.append("            <div class=\"col-md-12 col-sm-12 col-lg-10 col-lg-offset-1\">\n");
        stringBuilder.append("                <div class=\"panel panel-default qna-list\">\n");
        stringBuilder.append("                    <ul class=\"list\">\n");
        stringBuilder.append("                        <li>\n");
        stringBuilder.append("                            <div class=\"wrap\">\n");
        stringBuilder.append("                                <div class=\"main\">\n");
        stringBuilder.append("                                    <strong class=\"subject\">\n");
        stringBuilder.append("                                        <a href=\"./qna/show.html\">국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?</a>\n");
        stringBuilder.append("                                    </strong>\n");
        stringBuilder.append("                                    <div class=\"auth-info\">\n");
        stringBuilder.append("                                        <i class=\"icon-add-comment\"></i>\n");
        stringBuilder.append("                                        <span class=\"time\">2016-01-15 18:47</span>\n");
        stringBuilder.append("                                        <a href=\"./user/profile.html\" class=\"author\">자바지기</a>\n");
        stringBuilder.append("                                    </div>\n");
        stringBuilder.append("                                    <div class=\"reply\" title=\"댓글\">\n");
        stringBuilder.append("                                        <i class=\"icon-reply\"></i>\n");
        stringBuilder.append("                                        <span class=\"point\">8</span>\n");
        stringBuilder.append("                                    </div>\n");
        stringBuilder.append("                                </div>\n");
        stringBuilder.append("                            </div>\n");
        stringBuilder.append("                        </li>\n");
        stringBuilder.append("                        <li>\n");
        stringBuilder.append("                            <div class=\"wrap\">\n");
        stringBuilder.append("                                <div class=\"main\">\n");
        stringBuilder.append("                                    <strong class=\"subject\">\n");
        stringBuilder.append("                                        <a href=\"./qna/show.html\">runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?</a>\n");
        stringBuilder.append("                                    </strong>\n");
        stringBuilder.append("                                    <div class=\"auth-info\">\n");
        stringBuilder.append("                                        <i class=\"icon-add-comment\"></i>\n");
        stringBuilder.append("                                        <span class=\"time\">2016-01-05 18:47</span>\n");
        stringBuilder.append("                                        <a href=\"./user/profile.html\" class=\"author\">김문수</a>\n");
        stringBuilder.append("                                    </div>\n");
        stringBuilder.append("                                    <div class=\"reply\" title=\"댓글\">\n");
        stringBuilder.append("                                        <i class=\"icon-reply\"></i>\n");
        stringBuilder.append("                                        <span class=\"point\">12</span>\n");
        stringBuilder.append("                                    </div>\n");
        stringBuilder.append("                                </div>\n");
        stringBuilder.append("                            </div>\n");
        stringBuilder.append("                        </li>\n");
        stringBuilder.append("                    </ul>\n");
        stringBuilder.append("                    <div class=\"row\">\n");
        stringBuilder.append("                        <div class=\"col-md-3\"></div>\n");
        stringBuilder.append("                        <div class=\"col-md-6 text-center\">\n");
        stringBuilder.append("                            <ul class=\"pagination center-block\" style=\"display:inline-block;\">\n");
        stringBuilder.append("                                <li><a href=\"#\">«</a></li>\n");
        stringBuilder.append("                                <li><a href=\"#\">1</a></li>\n");
        stringBuilder.append("                                <li><a href=\"#\">2</a></li>\n");
        stringBuilder.append("                                <li><a href=\"#\">3</a></li>\n");
        stringBuilder.append("                                <li><a href=\"#\">4</a></li>\n");
        stringBuilder.append("                                <li><a href=\"#\">5</a></li>\n");
        stringBuilder.append("                                <li><a href=\"#\">»</a></li>\n");
        stringBuilder.append("                            </ul>\n");
        stringBuilder.append("                        </div>\n");
        stringBuilder.append("                        <div class=\"col-md-3 qna-write\">\n");
        stringBuilder.append("                            <a href=\"./qna/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>\n");
        stringBuilder.append("                        </div>\n");
        stringBuilder.append("                    </div>\n");
        stringBuilder.append("                </div>\n");
        stringBuilder.append("            </div>\n");
        stringBuilder.append("        </div>\n");
        stringBuilder.append("        <!-- script references -->\n");
        stringBuilder.append("        <script src=\"js/jquery-2.2.0.min.js\"></script>\n");
        stringBuilder.append("        <script src=\"js/bootstrap.min.js\"></script>\n");
        stringBuilder.append("        <script src=\"js/scripts.js\"></script>\n");
        stringBuilder.append("    </body>\n");
        stringBuilder.append("</html>\n");

        return stringBuilder.toString().getBytes();
    }

    private void makeLoginForm(UserResponseDto userResponseDto, StringBuilder stringBuilder) {
        if (userResponseDto != null) {
            stringBuilder.append("                        <li><a href=\"#\" role=\"button\">").append(userResponseDto.getName()).append("님</a></li>\n");
            stringBuilder.append("                        <li><a href=\"#\" role=\"button\">로그아웃</a></li>\n");
            stringBuilder.append("                        <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\n");
        } else {
            stringBuilder.append("                        <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>\n");
            stringBuilder.append("                        <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>\n");
        }
    }
}
