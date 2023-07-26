package support.web.view;

import db.Database;
import model.Post;
import model.Session;
import support.annotation.Container;
import utils.LoginUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.List;

@Container
public class IndexView implements View {

    @Override
    public String view(HttpRequest request, HttpResponse response) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("<!DOCTYPE html>")
                .append("<html lang=\"kr\">")
                .append("	<head>")
                .append("		<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">")
                .append("		<meta charset=\"utf-8\">")
                .append("		<title>SLiPP Java Web Programming</title>")
                .append("		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">")
                .append("		<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">")
                .append("		<!--[if lt IE 9]>")
                .append("			<script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>")
                .append("		<![endif]-->")
                .append("		<link href=\"css/styles.css\" rel=\"stylesheet\">")
                .append("	</head>")
                .append("	<body>")
                .append("<nav class=\"navbar navbar-fixed-top header\">")
                .append(" 	<div class=\"col-md-12\">")
                .append("        <div class=\"navbar-header\">")
                .append("            <a href=\"index\" class=\"navbar-brand\">SLiPP</a>")
                .append("          <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">")
                .append("          <i class=\"glyphicon glyphicon-search\"></i>")
                .append("          </button>")
                .append("        </div>")
                .append("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">")
                .append("          <form class=\"navbar-form pull-left\">")
                .append("              <div class=\"input-group\" style=\"max-width:470px;\">")
                .append("                <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">")
                .append("                <div class=\"input-group-btn\">")
                .append("                  <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>")
                .append("                </div>")
                .append("              </div>")
                .append("          </form>")
                .append("          <ul class=\"nav navbar-nav navbar-right\">")
                .append("             <li>")
                .append("                <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>")
                .append("                <ul class=\"dropdown-menu\">")
                .append("                  <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>")
                .append("                  <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>")
                .append("                </ul>")
                .append("             </li>")
                .append("             <li><a href=\"./user/list\"><i class=\"glyphicon glyphicon-user\"></i></a></li>")
                .append("           </ul>")
                .append("        </div>")
                .append("     </div>")
                .append("</nav>")
                .append("<div class=\"navbar navbar-default\" id=\"subnav\">")
                .append("    <div class=\"col-md-12\">")
                .append("        <div class=\"navbar-header\">")
                .append("            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>")
                .append("            <ul class=\"nav dropdown-menu\">")
                .append("                <li><a href=\"user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>")
                .append("                <li class=\"nav-divider\"></li>")
                .append("                <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>")
                .append("            </ul>")
                .append("            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">")
                .append("            	<span class=\"sr-only\">Toggle navigation</span>")
                .append("            	<span class=\"icon-bar\"></span>")
                .append("            	<span class=\"icon-bar\"></span>")
                .append("            	<span class=\"icon-bar\"></span>")
                .append("            </button>")
                .append("        </div>")
                .append("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">")
                .append("            <ul class=\"nav navbar-nav navbar-right\">");

        Session loginSession = LoginUtils.getLoginSession(request);
        if (loginSession != null) {
            stringBuilder.append("                <li><a>").append(loginSession.getUser().getName()).append("</a></li>");
        }
        stringBuilder
                .append("                <li class=\"active\"><a href=\"index\">Posts</a></li>");
        if (loginSession == null) {
            stringBuilder.append("                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>");
        }
        stringBuilder
                .append("                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>")
                .append("                <!--")
                .append("                <li><a href=\"#loginModal\" role=\"button\" data-toggle=\"modal\">로그인</a></li>")
                .append("                <li><a href=\"#registerModal\" role=\"button\" data-toggle=\"modal\">회원가입</a></li>")
                .append("                -->")
                .append("                <li><a href=\"#\" role=\"button\">로그아웃</a></li>")
                .append("                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>")
                .append("            </ul>")
                .append("        </div>")
                .append("    </div>")
                .append("</div>")
                .append("<div class=\"container\" id=\"main\">")
                .append("   <div class=\"col-md-12 col-sm-12 col-lg-10 col-lg-offset-1\">")
                .append("      <div class=\"panel panel-default qna-list\">")
                .append("          <ul class=\"list\">");

        List<Post> posts = Database.findAllPost();
        posts.forEach(
                post -> {
                    stringBuilder
                            .append("              <li>")
                            .append("                  <div class=\"wrap\">")
                            .append("                      <div class=\"main\">")
                            .append("                          <strong class=\"subject\">")
                            .append("                              <a href=\"post/show\">").append(post.getTitle()).append("</a>")
                            .append("                          </strong>")
                            .append("                          <div class=\"auth-info\">")
                            .append("                              <i class=\"icon-add-comment\"></i>")
                            .append("                              <span class=\"time\">").append(post.getCreateDateTime()).append("</span>")
                            .append("                              <a href=\"./user/profile.html\" class=\"author\">").append(post.getWriter()).append("</a>")
                            .append("                          </div>")
                            .append("                          <div class=\"reply\" title=\"댓글\">")
                            .append("                              <i class=\"icon-reply\"></i>")
                            .append("                              <span class=\"point\">8</span>")
                            .append("                          </div>")
                            .append("                      </div>")
                            .append("                  </div>")
                            .append("              </li>");
                }
        );

        stringBuilder
                .append("          </ul>")
                .append("          <div class=\"row\">")
                .append("              <div class=\"col-md-3\"></div>")
                .append("              <div class=\"col-md-6 text-center\">")
                .append("                  <ul class=\"pagination center-block\" style=\"display:inline-block;\">")
                .append("                      <li><a href=\"#\">«</a></li>")
                .append("                      <li><a href=\"#\">1</a></li>")
                .append("                      <li><a href=\"#\">2</a></li>")
                .append("                      <li><a href=\"#\">3</a></li>")
                .append("                      <li><a href=\"#\">4</a></li>")
                .append("                      <li><a href=\"#\">5</a></li>")
                .append("                      <li><a href=\"#\">»</a></li>")
                .append("                </ul>")
                .append("              </div>")
                .append("              <div class=\"col-md-3 qna-write\">")
                .append("                  <a href=\"post/form\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>")
                .append("              </div>")
                .append("          </div>")
                .append("        </div>")
                .append("    </div>")
                .append("</div>")
                .append("<!-- script references -->")
                .append("<script src=\"js/jquery-2.2.0.min.js\"></script>")
                .append("<script src=\"js/bootstrap.min.js\"></script>")
                .append("<script src=\"js/scripts.js\"></script>")
                .append("	</body>")
                .append("</html>");

        return stringBuilder.toString();
    }

}
