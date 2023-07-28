package view;

import db.UserDatabase;
import http.HttpSession;
import model.User;

public class View {

    public static byte[] getIndex(HttpSession session) {
        User user = (User) session.getAttributes("user");
        String name = user.getName();

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html><html lang=\"kr\"><head>")
                .append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">")
                .append("<meta charset=\"utf-8\">")
                .append("<title>SLiPP Java Web Programming</title>")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">")
                .append("<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">")
                .append("<!--[if lt IE 9]><script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script><![endif]-->")
                .append("<link href=\"css/styles.css\" rel=\"stylesheet\"></head>")
                .append("<body><nav class=\"navbar navbar-fixed-top header\"><div class=\"col-md-12\">")
                .append("<div class=\"navbar-header\"><a href=\"index.html\" class=\"navbar-brand\">SLiPP</a>")
                .append("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">")
                .append("<i class=\"glyphicon glyphicon-search\"></i></button></div>")
                .append("<div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">")
                .append("<form class=\"navbar-form pull-left\"><div class=\"input-group\" style=\"max-width:470px\">")
                .append("<input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">")
                .append("<div class=\"input-group-btn\"><button class=\"btn btn-default btn-primary\" type=\"submit\">")
                .append("<i class=\"glyphicon glyphicon-search\"></i></button></div></div></form>")
                .append("<ul class=\"nav navbar-nav navbar-right\">")
                .append("<li><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>")
                .append("<ul class=\"dropdown-menu\">")
                .append("<li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>")
                .append("<li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li></ul></li>")
                .append("<li><a href=\"./user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li></ul></div></div></nav>")
                .append("<div class=\"navbar navbar-default\" id=\"subnav\"><div class=\"col-md-12\">")
                .append("<div class=\"navbar-header\">")
                .append("<a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\">")
                .append("<i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>")
                .append("<ul class=\"nav dropdown-menu\">")
                .append("<li><a href=\"user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>")
                .append("<li class=\"nav-divider\"></li>")
                .append("<li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li></ul>")
                .append("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">")
                .append("<span class=\"sr-only\">Toggle navigation</span>")
                .append("<span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button></div>")
                .append("<div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">")
                .append("<ul class=\"nav navbar-nav navbar-right\">")
                .append("<li><p class=\"navbar-text\">").append(name).append("님 환영합니다.</p></li>")
                .append("<li class=\"active\"><a href=\"index.html\">Posts</a></li>")
                .append("<li><form method=\"post\" action=\"../user/logout\"> <button type=\"submit\" role=\"button\">로그아웃</button> </form></li>")
                .append("<li><a href=\"user/profile.html\" role=\"button\">개인정보수정</a></li></ul></div></div></div>")
                .append("<div class=\"container\" id=\"main\"><div class=\"col-md-12 col-sm-12 col-lg-10 col-lg-offset-1\">")
                .append("<div class=\"panel panel-default qna-list\"><ul class=\"list\"><li>")
                .append("<div class=\"wrap\"><div class=\"main\">")
                .append("<strong class=\"subject\"><a href=\"./qna/show.html\">국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?</a></strong>")
                .append("<div class=\"auth-info\"><i class=\"icon-add-comment\"></i><span class=\"time\">2016-01-15 18:47</span>")
                .append("<a href=\"./user/profile.html\" class=\"author\">자바지기</a></div>")
                .append("<div class=\"reply\" title=\"댓글\"><i class=\"icon-reply\"></i><span class=\"point\">8</span></div>")
                .append("</div></div></li><li><div class=\"wrap\"><div class=\"main\">")
                .append("<strong class=\"subject\"><a href=\"./qna/show.html\">runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?</a></strong>")
                .append("<div class=\"auth-info\"><i class=\"icon-add-comment\"></i><span class=\"time\">2016-01-05 18:47</span>")
                .append("<a href=\"./user/profile.html\" class=\"author\">김문수</a></div>")
                .append("<div class=\"reply\" title=\"댓글\"><i class=\"icon-reply\"></i><span class=\"point\">12</span></div>")
                .append("</div></div></li></ul><div class=\"row\">")
                .append("<div class=\"col-md-3\"></div><div class=\"col-md-6 text-center\">")
                .append("<ul class=\"pagination center-block\" style=\"display:inline-block;\">")
                .append("<li><a href=\"#\">«</a></li>")
                .append("<li><a href=\"#\">1</a></li><li><a href=\"#\">2</a></li>")
                .append("<li><a href=\"#\">3</a></li><li><a href=\"#\">4</a></li><li><a href=\"#\">5</a></li>")
                .append("<li><a href=\"#\">»</a></li></ul></div>")
                .append("<div class=\"col-md-3 qna-write\">")
                .append("<a href=\"./qna/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>")
                .append("</div></div></div></div></div>")
                .append("<script src=\"js/jquery-2.2.0.min.js\"></script>")
                .append("<script src=\"js/bootstrap.min.js\"></script>")
                .append("<script src=\"js/scripts.js\"></script>")
                .append("</body></html>");

        return htmlBuilder.toString().getBytes();
    }

    public static byte[] getUserList() {
        StringBuilder htmlBuilder = new StringBuilder();
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
        htmlBuilder.append("                <li class=\"active\"><a href=\"../index.html\">Posts</a></li>\n");
        htmlBuilder.append("<li><form method=\"post\" action=\"../user/logout\"> <button type=\"submit\" role=\"button\">로그아웃</button> </form></li>");
        htmlBuilder.append("                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\n");
        htmlBuilder.append("            </ul>\n");
        htmlBuilder.append("        </div>\n");
        htmlBuilder.append("    </div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("<div class=\"container\" id=\"main\">\n");
        htmlBuilder.append("   <div class=\"col-md-10 col-md-offset-1\">\n");
        htmlBuilder.append("      <div class=\"panel panel-default\">\n");
        htmlBuilder.append("          <table class=\"table table-hover\">\n");
        htmlBuilder.append("              <thead>\n");
        htmlBuilder.append("                <tr>\n");
        htmlBuilder.append("                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>\n");
        htmlBuilder.append("                </tr>\n");
        htmlBuilder.append("              </thead>\n");
        htmlBuilder.append("              <tbody>\n");
        int i = 0;
        for (User user : UserDatabase.findAll()) {
            htmlBuilder.append("                <tr>\n");
            htmlBuilder.append("                    <th scope=\"row\">" + i + "</th> <td>" + user.getUserId() + "</td> <td>" + user.getName() + "</td> <td>" + user.getEmail() + "</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n");
            htmlBuilder.append("                </tr>\n");

        }
        htmlBuilder.append("              </tbody>\n");
        htmlBuilder.append("          </table>\n");
        htmlBuilder.append("        </div>\n");
        htmlBuilder.append("    </div>\n");
        htmlBuilder.append("</div>\n");
        htmlBuilder.append("\n");
        htmlBuilder.append("<!-- script references -->\n");
        htmlBuilder.append("<script src=\"../js/jquery-2.2.0.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/bootstrap.min.js\"></script>\n");
        htmlBuilder.append("<script src=\"../js/scripts.js\"></script>\n");
        htmlBuilder.append("\t</body>\n");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString().getBytes();
    }
}
