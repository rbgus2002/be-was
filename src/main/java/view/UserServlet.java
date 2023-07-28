package view;

import dto.UserResponseDto;

import java.util.List;
import java.util.Map;

public class UserServlet implements Servlet {
    @Override
    public byte[] doGet(Map<String, Object> viewParameters) {
        List<UserResponseDto> users = (List<UserResponseDto>) viewParameters.get("users");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>\n");
        stringBuilder.append("<html lang=\"kr\">\n");
        stringBuilder.append("<head>\n");
        stringBuilder.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n");
        stringBuilder.append("<meta charset=\"utf-8\">\n");
        stringBuilder.append("<title>SLiPP Java Web Programming</title>\n");
        stringBuilder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n");
        stringBuilder.append("<link href=\"../css/bootstrap.min.css\" rel=\"stylesheet\">\n");
        stringBuilder.append("<!--[if lt IE 9]>\n");
        stringBuilder.append("<script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n");
        stringBuilder.append("<![endif]-->\n");
        stringBuilder.append("<link href=\"../css/styles.css\" rel=\"stylesheet\">\n");
        stringBuilder.append("</head>\n");
        stringBuilder.append("<body>\n");
        stringBuilder.append("<nav class=\"navbar navbar-fixed-top header\">\n");
        stringBuilder.append("<div class=\"col-md-12\">\n");
        stringBuilder.append("<div class=\"navbar-header\">\n");
        stringBuilder.append("<a href=\"../index.html\" class=\"navbar-brand\">SLiPP</a>\n");
        stringBuilder.append("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">\n");
        stringBuilder.append("<i class=\"glyphicon glyphicon-search\"></i>\n");
        stringBuilder.append("</button>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("<div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">\n");
        stringBuilder.append("<form class=\"navbar-form pull-left\">\n");
        stringBuilder.append("<div class=\"input-group\" style=\"max-width:470px;\">\n");
        stringBuilder.append("<input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\n");
        stringBuilder.append("<div class=\"input-group-btn\">\n");
        stringBuilder.append("<button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("</form>\n");
        stringBuilder.append("<ul class=\"nav navbar-nav navbar-right\">\n");
        stringBuilder.append("<li>\n");
        stringBuilder.append("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>\n");
        stringBuilder.append("<ul class=\"dropdown-menu\">\n");
        stringBuilder.append("<li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>\n");
        stringBuilder.append("<li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>\n");
        stringBuilder.append("</ul>\n");
        stringBuilder.append("</li>\n");
        stringBuilder.append("<li><a href=\"../user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li>\n");
        stringBuilder.append("</ul>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("</nav>\n");
        stringBuilder.append("<div class=\"navbar navbar-default\" id=\"subnav\">\n");
        stringBuilder.append("<div class=\"col-md-12\">\n");
        stringBuilder.append("<div class=\"navbar-header\">\n");
        stringBuilder.append("<a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>\n");
        stringBuilder.append("<ul class=\"nav dropdown-menu\">\n");
        stringBuilder.append("<li><a href=\"../user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>\n");
        stringBuilder.append("<li class=\"nav-divider\"></li>\n");
        stringBuilder.append("<li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>\n");
        stringBuilder.append("</ul>\n");
        stringBuilder.append("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">\n");
        stringBuilder.append("<span class=\"sr-only\">Toggle navigation</span>\n");
        stringBuilder.append("<span class=\"icon-bar\"></span>\n");
        stringBuilder.append("<span class=\"icon-bar\"></span>\n");
        stringBuilder.append("<span class=\"icon-bar\"></span>\n");
        stringBuilder.append("</button>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("<div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">\n");
        stringBuilder.append("<ul class=\"nav navbar-nav navbar-right\">\n");
        stringBuilder.append("<li class=\"active\"><a href=\"../index.html\">Posts</a></li>\n");
        stringBuilder.append("<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>\n");
        stringBuilder.append("<li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>\n");
        stringBuilder.append("<li><a href=\"#\" role=\"button\">로그아웃</a></li>\n");
        stringBuilder.append("<li><a href=\"#\" role=\"button\">개인정보수정</a></li>\n");
        stringBuilder.append("</ul>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("</div>\n");

        stringBuilder.append("<div class=\"container\" id=\"main\">\n");
        stringBuilder.append("<div class=\"col-md-10 col-md-offset-1\">\n");
        stringBuilder.append("<div class=\"panel panel-default\">\n");
        stringBuilder.append("<table class=\"table table-hover\">\n");
        stringBuilder.append("<thead>\n");
        stringBuilder.append("<tr>\n");
        stringBuilder.append("<th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>\n");
        stringBuilder.append("</tr>\n");
        stringBuilder.append("</thead>\n");
        stringBuilder.append("<tbody>\n");

        for (var userResponse : users) {
            stringBuilder.append("<tr>\n");
            stringBuilder.append("<th scope=\"row\">1</th> <td>").append(userResponse.getUserId()).append("</td> <td>").append(userResponse.getName()).append("</td> <td>").append(userResponse.getEmail()).append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n");
            stringBuilder.append("</tr>\n");
        }

        stringBuilder.append("</tbody>\n");
        stringBuilder.append("</table>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("</div>\n");
        stringBuilder.append("</div>\n");

        stringBuilder.append("<script src=\"../js/jquery-2.2.0.min.js\"></script>\n");
        stringBuilder.append("<script src=\"../js/bootstrap.min.js\"></script>\n");
        stringBuilder.append("<script src=\"../js/scripts.js\"></script>\n");
        stringBuilder.append("</body>\n");
        stringBuilder.append("</html>");

        return stringBuilder.toString().getBytes();
    }

}
