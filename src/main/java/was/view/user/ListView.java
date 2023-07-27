package was.view.user;

import java.util.Collection;

import was.webserver.annotation.Controller;
import was.controller.annotation.RequestMapping;
import was.db.Database;
import was.model.User;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;
import was.webserver.session.HttpSession;
import was.webserver.session.SessionData;
import was.webserver.utils.HttpHeader;
import was.webserver.utils.HttpMethod;
import was.webserver.utils.HttpMimeType;
import was.webserver.utils.HttpStatus;

@Controller
public class ListView {

	@RequestMapping(method = HttpMethod.GET, path = "/user/list")
	public void list(HttpWasRequest request, HttpWasResponse response) {
		final HttpSession httpSession = HttpSession.getInstance();
		final String sessionId = request.getSessionId();

		if (!httpSession.verifySession(sessionId)) {
			response.setHttpStatus(HttpStatus.FOUND);
			response.addHeader(HttpHeader.LOCATION, "/user/login.html");
			return;
		}

		final SessionData sessionData = httpSession.getSessionData(sessionId);
		final String sessionUserId = sessionData.getUserId();
		final User findUser = Database.findUserById(sessionUserId);

		final Collection<User> users = Database.findUserAll();

		StringBuilder sb = new StringBuilder();

		sb.append("<!DOCTYPE html>\r\n")
			.append("<html lang=\"kr\">\r\n")
			.append("<head>\r\n")
			.append("    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n")
			.append("    <meta charset=\"utf-8\">\r\n")
			.append("    <title>SLiPP Java Web Programming</title>\r\n")
			.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n")
			.append("    <link href=\"../css/bootstrap.min.css\" rel=\"stylesheet\">\r\n")
			.append("    <!--[if lt IE 9]>\r\n")
			.append("    <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n")
			.append("    <![endif]-->\r\n")
			.append("    <link href=\"../css/styles.css\" rel=\"stylesheet\">\r\n")
			.append("</head>\r\n")
			.append("<body>\r\n")
			.append("<nav class=\"navbar navbar-fixed-top header\">\r\n")
			.append("    <div class=\"col-md-12\">\r\n")
			.append("        <div class=\"navbar-header\">\r\n")
			.append("\r\n")
			.append("            <a href=\"../index.html\" class=\"navbar-brand\">SLiPP</a>\r\n")
			.append(
				"            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">\r\n")
			.append("                <i class=\"glyphicon glyphicon-search\"></i>\r\n")
			.append("            </button>\r\n")
			.append("\r\n")
			.append("        </div>\r\n")
			.append("        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">\r\n")
			.append("            <form class=\"navbar-form pull-left\">\r\n")
			.append("                <div class=\"input-group\" style=\"max-width:470px;\">\r\n")
			.append(
				"                    <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\r\n")
			.append("                    <div class=\"input-group-btn\">\r\n")
			.append(
				"                        <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\r\n")
			.append("                    </div>\r\n")
			.append("                </div>\r\n")
			.append("            </form>\r\n")
			.append("            <ul class=\"nav navbar-nav navbar-right\">\r\n")
			.append("                <li>\r\n")
			.append(
				"                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>\r\n")
			.append("                    <ul class=\"dropdown-menu\">\r\n")
			.append("                        <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>\r\n")
			.append(
				"                        <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>\r\n")
			.append("                    </ul>\r\n")
			.append("                </li>\r\n")
			.append(
				"                <li><a href=\"../user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li>\r\n")
			.append("            </ul>\r\n")
			.append("        </div>\r\n")
			.append("    </div>\r\n")
			.append("</nav>\r\n")
			.append("<div class=\"navbar navbar-default\" id=\"subnav\">\r\n")
			.append("    <div class=\"col-md-12\">\r\n")
			.append("        <div class=\"navbar-header\">\r\n")
			.append(
				"            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>\r\n")
			.append("            <ul class=\"nav dropdown-menu\">\r\n")
			.append(
				"                <li><a href=\"../user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>\r\n")
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
			.append("            <ul class=\"nav navbar-nav navbar-right\">\r\n")
			.append("                <li class=\"active\"><a href=\"../index.html\">Posts</a></li>\r\n")
			.append("                <li><a role=\"button\">")
			.append(findUser.getName())
			.append("</a></li>\r\n")
			.append("                <li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>\r\n")
			.append("                <li><a href=\"#\" role=\"button\">로그아웃</a></li>\r\n")
			.append("                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\r\n")
			.append("            </ul>\r\n")
			.append("        </div>\r\n")
			.append("    </div>\r\n")
			.append("</div>\r\n")
			.append("\r\n")
			.append("<div class=\"container\" id=\"main\">\r\n")
			.append("   <div class=\"col-md-10 col-md-offset-1\">\r\n")
			.append("      <div class=\"panel panel-default\">\r\n")
			.append("          <table class=\"table table-hover\">\r\n")
			.append("              <thead>\r\n")
			.append("                <tr>\r\n")
			.append("                    <th>#</th> <th>사용자 아이디</th> <th>이름</th> <th>이메일</th><th></th>\r\n")
			.append("                </tr>\r\n")
			.append("              </thead>\r\n")
			.append("              <tbody>\r\n");
		int a = 3;
		for (User user : users) {
			sb.append("                <tr>\r\n")
				.append("                    <th scope=\"row\">")
				.append(a++)
				.append("</th><td>")
				.append(user.getUserId())
				.append("</td> <td>")
				.append(user.getName())
				.append("</td> <td>")
				.append(user.getEmail())
				.append("</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\r\n")
				.append("                </tr>\r\n");
		}
			sb.append("              </tbody>\r\n")
			.append("          </table>\r\n")
			.append("        </div>\r\n")
			.append("    </div>\r\n")
			.append("</div>\r\n")
			.append("\r\n")
			.append("<!-- script references -->\r\n")
			.append("<script src=\"../js/jquery-2.2.0.min.js\"></script>\r\n")
			.append("<script src=\"../js/bootstrap.min.js\"></script>\r\n")
			.append("<script src=\"../js/scripts.js\"></script>\r\n")
			.append("\t</body>\r\n")
			.append("</html>");

		response.setHttpStatus(HttpStatus.OK);
		response.setBody(sb.toString(), HttpMimeType.HTML);
	}
}
