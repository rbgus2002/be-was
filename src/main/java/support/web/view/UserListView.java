package support.web.view;

import db.Database;
import model.Session;
import support.annotation.Component;
import support.web.Model;
import utils.StringBuilderExpansion;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.List;

@Component
public class UserListView implements View {

    @Override
    public String getName() {
        return "/user/list";
    }

    public String render(HttpRequest request, HttpResponse response, Model model) {
        StringBuilderExpansion stringBuilder = new StringBuilderExpansion();
        stringBuilder
                .appendCRLF("<!DOCTYPE html>")
                .appendCRLF("<html lang=\"kr\">")
                .appendCRLF("<head>")
                .appendCRLF("    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">")
                .appendCRLF("    <meta charset=\"utf-8\">")
                .appendCRLF("    <title>SLiPP Java Web Programming</title>")
                .appendCRLF("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">")
                .appendCRLF("    <link href=\"../css/bootstrap.min.css\" rel=\"stylesheet\">")
                .appendCRLF("    <!--[if lt IE 9]>")
                .appendCRLF("    <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>")
                .appendCRLF("    <![endif]-->")
                .appendCRLF("    <link href=\"../css/styles.css\" rel=\"stylesheet\">")
                .appendCRLF("</head>")
                .appendCRLF("<body>")
                .appendCRLF("<nav class=\"navbar navbar-fixed-top header\">")
                .appendCRLF("    <div class=\"col-md-12\">")
                .appendCRLF("        <div class=\"navbar-header\">")
                .appendCRLF("            <a href=\"../index\" class=\"navbar-brand\">SLiPP</a>")
                .appendCRLF("            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">")
                .appendCRLF("                <i class=\"glyphicon glyphicon-search\"></i>")
                .appendCRLF("            </button>")
                .appendCRLF("        </div>")
                .appendCRLF("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">")
                .appendCRLF("            <form class=\"navbar-form pull-left\">")
                .appendCRLF("                <div class=\"input-group\" style=\"max-width:470px;\">")
                .appendCRLF("                    <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">")
                .appendCRLF("                    <div class=\"input-group-btn\">")
                .appendCRLF("                        <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>")
                .appendCRLF("                    </div>")
                .appendCRLF("                </div>")
                .appendCRLF("            </form>")
                .appendCRLF("            <ul class=\"nav navbar-nav navbar-right\">")
                .appendCRLF("                <li>")
                .appendCRLF("                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>")
                .appendCRLF("                    <ul class=\"dropdown-menu\">")
                .appendCRLF("                        <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>")
                .appendCRLF("                        <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>")
                .appendCRLF("                    </ul>")
                .appendCRLF("                </li>")
                .appendCRLF("                <li><a href=\"../user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li>")
                .appendCRLF("            </ul>")
                .appendCRLF("        </div>")
                .appendCRLF("    </div>")
                .appendCRLF("</nav>")
                .appendCRLF("<div class=\"navbar navbar-default\" id=\"subnav\">")
                .appendCRLF("    <div class=\"col-md-12\">")
                .appendCRLF("        <div class=\"navbar-header\">")
                .appendCRLF("            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>")
                .appendCRLF("            <ul class=\"nav dropdown-menu\">")
                .appendCRLF("                <li><a href=\"../user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>")
                .appendCRLF("                <li class=\"nav-divider\"></li>")
                .appendCRLF("                <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>")
                .appendCRLF("            </ul>")
                .appendCRLF("            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">")
                .appendCRLF("            	<span class=\"sr-only\">Toggle navigation</span>")
                .appendCRLF("            	<span class=\"icon-bar\"></span>")
                .appendCRLF("            	<span class=\"icon-bar\"></span>")
                .appendCRLF("            	<span class=\"icon-bar\"></span>")
                .appendCRLF("            </button>")
                .appendCRLF("        </div>")
                .appendCRLF("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">")
                .appendCRLF("            <ul class=\"nav navbar-nav navbar-right\">")
                .appendCRLF("                <li class=\"active\"><a href=\"../index\">Posts</a></li>")
                .appendCRLF("                <li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>")
                .appendCRLF("                <li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>")
                .appendCRLF("                <li><a href=\"#\" role=\"button\">로그아웃</a></li>")
                .appendCRLF("                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>")
                .appendCRLF("            </ul>")
                .appendCRLF("        </div>")
                .appendCRLF("    </div>")
                .appendCRLF("</div>")
                .appendCRLF("<div class=\"container\" id=\"main\">")
                .appendCRLF("   <div class=\"col-md-10 col-md-offset-1\">")
                .appendCRLF("      <div class=\"panel panel-default\">")
                .appendCRLF("          <table class=\"table table-hover\">")
                .appendCRLF("              <thead>")
                .appendCRLF("                <tr>")
                .appendCRLF("                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>")
                .appendCRLF("                </tr>")
                .appendCRLF("              </thead>")
                .appendCRLF("              <tbody>");

        List<Session> sessions = Database.findAllSession();
        int count = 0;
        for (Session session : sessions) {
            stringBuilder
                    .appendCRLF("                <tr>")
                    .appendCRLF("                    <th scope=\"row\">")
                    .appendCRLF(count++)
                    .appendCRLF("</th> <td>")
                    .appendCRLF(session.getUser().getUserId())
                    .appendCRLF("</td> <td>")
                    .appendCRLF(session.getUser().getName())
                    .appendCRLF("</td> <td>")
                    .appendCRLF(session.getUser().getEmail())
                    .appendCRLF("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>")
                    .appendCRLF("                </tr>");
        }

        stringBuilder
                .appendCRLF("              </tbody>")
                .appendCRLF("          </table>")
                .appendCRLF("        </div>")
                .appendCRLF("    </div>")
                .appendCRLF("</div>")
                .appendCRLF("<!-- script references -->")
                .appendCRLF("<script src=\"../js/jquery-2.2.0.min.js\"></script>")
                .appendCRLF("<script src=\"../js/bootstrap.min.js\"></script>")
                .appendCRLF("<script src=\"../js/scripts.js\"></script>")
                .appendCRLF("	</body>")
                .appendCRLF("</html>");

        return stringBuilder.toString();
    }

}
