package was.view.board;

import was.controller.annotation.RequestMapping;
import was.db.Database;
import was.model.Board;
import was.model.User;
import was.webserver.annotation.View;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;
import was.webserver.session.HttpSession;
import was.webserver.session.SessionData;
import was.webserver.utils.HttpHeader;
import was.webserver.utils.HttpMethod;
import was.webserver.utils.HttpMimeType;
import was.webserver.utils.HttpStatus;

@View
public class PostView {

	@RequestMapping(method = HttpMethod.GET, path = "/post")
	public void post(HttpWasRequest request, HttpWasResponse response) {
		final HttpSession httpSession = HttpSession.getInstance();
		final String sessionId = request.getSessionId();

		if (!httpSession.verifySession(sessionId)) {
			response.setHttpStatus(HttpStatus.FOUND);
			response.addHeader(HttpHeader.LOCATION, "/user/login.html");
			return;
		}

		final String index = request.getParameter("index");
		if (index == null || index.isBlank()) {
			response.responseResource("/status/404.html", HttpStatus.NOT_FOUND);
			return;
		}
		final Board board = Database.findBoardByIndex(Integer.parseInt(index));
		if (board == null) {
			response.responseResource("/status/404.html", HttpStatus.NOT_FOUND);
			return;
		}

		StringBuilder sb = new StringBuilder();
		final SessionData sessionData = httpSession.getSessionData(sessionId);
		final String userId = sessionData.getUserId();
		final User user = Database.findUserById(userId);
		createHeader(sb, user.getName());
		createBoard(sb, board);
		createFooter(sb);

		response.setHttpStatus(HttpStatus.OK);
		response.setBody(sb.toString(), HttpMimeType.HTML);
	}

	private void createHeader(StringBuilder sb, String userName) {
		sb.append("<!DOCTYPE html>\r\n"
			+ "<html lang=\"kr\">\r\n"
			+ "<head>\r\n"
			+ "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n"
			+ "    <meta charset=\"utf-8\">\r\n"
			+ "    <title>SLiPP Java Web Programming</title>\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n"
			+ "    <link href=\"../css/bootstrap.min.css\" rel=\"stylesheet\">\r\n"
			+ "    <!--[if lt IE 9]>\r\n"
			+ "    <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\r\n"
			+ "    <![endif]-->\r\n"
			+ "    <link href=\"../css/styles.css\" rel=\"stylesheet\">\r\n"
			+ "</head>\r\n"
			+ "<body>\r\n"
			+ "<nav class=\"navbar navbar-fixed-top header\">\r\n"
			+ "    <div class=\"col-md-12\">\r\n"
			+ "        <div class=\"navbar-header\">\r\n"
			+ "            <a href=\"../index.html\" class=\"navbar-brand\">SLiPP</a>\r\n"
			+ "            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse1\">\r\n"
			+ "                <i class=\"glyphicon glyphicon-search\"></i>\r\n"
			+ "            </button>\r\n"
			+ "        </div>\r\n"
			+ "        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse1\">\r\n"
			+ "            <form class=\"navbar-form pull-left\">\r\n"
			+ "                <div class=\"input-group\" style=\"max-width:470px;\">\r\n"
			+ "                    <input type=\"text\" class=\"form-control\" placeholder=\"Search\" name=\"srch-term\" id=\"srch-term\">\r\n"
			+ "                    <div class=\"input-group-btn\">\r\n"
			+ "                        <button class=\"btn btn-default btn-primary\" type=\"submit\"><i class=\"glyphicon glyphicon-search\"></i></button>\r\n"
			+ "                    </div>\r\n"
			+ "                </div>\r\n"
			+ "            </form>\r\n"
			+ "            <ul class=\"nav navbar-nav navbar-right\">\r\n"
			+ "                <li>\r\n"
			+ "                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-bell\"></i></a>\r\n"
			+ "                    <ul class=\"dropdown-menu\">\r\n"
			+ "                        <li><a href=\"https://slipp.net\" target=\"_blank\">SLiPP</a></li>\r\n"
			+ "                        <li><a href=\"https://facebook.com\" target=\"_blank\">Facebook</a></li>\r\n"
			+ "                    </ul>\r\n"
			+ "                </li>\r\n"
			+ "                <li><a href=\"../user/list.html\"><i class=\"glyphicon glyphicon-user\"></i></a></li>\r\n"
			+ "            </ul>\r\n"
			+ "        </div>\r\n"
			+ "    </div>\r\n"
			+ "</nav>\r\n"
			+ "<div class=\"navbar navbar-default\" id=\"subnav\">\r\n"
			+ "    <div class=\"col-md-12\">\r\n"
			+ "        <div class=\"navbar-header\">\r\n"
			+ "            <a href=\"#\" style=\"margin-left:15px;\" class=\"navbar-btn btn btn-default btn-plus dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"glyphicon glyphicon-home\" style=\"color:#dd1111;\"></i> Home <small><i class=\"glyphicon glyphicon-chevron-down\"></i></small></a>\r\n"
			+ "            <ul class=\"nav dropdown-menu\">\r\n"
			+ "                <li><a href=\"../user/profile.html\"><i class=\"glyphicon glyphicon-user\" style=\"color:#1111dd;\"></i> Profile</a></li>\r\n"
			+ "                <li class=\"nav-divider\"></li>\r\n"
			+ "                <li><a href=\"#\"><i class=\"glyphicon glyphicon-cog\" style=\"color:#dd1111;\"></i> Settings</a></li>\r\n"
			+ "            </ul>\r\n"
			+ "            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-collapse2\">\r\n"
			+ "            \t<span class=\"sr-only\">Toggle navigation</span>\r\n"
			+ "            \t<span class=\"icon-bar\"></span>\r\n"
			+ "            \t<span class=\"icon-bar\"></span>\r\n"
			+ "            \t<span class=\"icon-bar\"></span>\r\n"
			+ "            </button>\r\n"
			+ "        </div>\r\n"
			+ "        <div class=\"collapse navbar-collapse\" id=\"navbar-collapse2\">\r\n"
			+ "            <ul class=\"nav navbar-nav navbar-right\">\r\n"
			+ "                <li class=\"active\"><a href=\"../index.html\">Posts</a></li>\r\n"
			+ "                <li><a href=\"../user/login.html\" role=\"button\">"+userName+"</a></li>\r\n"
			+ "                <li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>\r\n"
			+ "                <li><a href=\"#\" role=\"button\">로그아웃</a></li>\r\n"
			+ "                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\r\n"
			+ "            </ul>\r\n"
			+ "        </div>\r\n"
			+ "    </div>\r\n"
			+ "</div>\r\n"
			+ "\n\r");
	}

	private void createBoard(StringBuilder sb, Board board) {
		sb.append("<div class=\"container\" id=\"main\">\r\n")
			.append("    <div class=\"col-md-12 col-sm-12 col-lg-12\">\r\n")
			.append("        <div class=\"panel panel-default\">\r\n")
			.append("            <header class=\"qna-header\">\r\n")
			.append(
				"                <h2 class=\"qna-title\">" + board.getTitle() + "</h2>\r\n")
			.append("            </header>\r\n")
			.append("            <div class=\"content-main\">\r\n")
			.append("                <article class=\"article\">\r\n")
			.append("                    <div class=\"article-header\">\r\n")
			.append("                        <div class=\"article-header-thumb\">\r\n")
			.append(
				"                            <img src=\"https://graph.facebook.com/v2.3/100000059371774/picture\"\r\n")
			.append("                                 class=\"article-author-thumb\" alt=\"\">\r\n")
			.append("                        </div>\r\n")
			.append("                        <div class=\"article-header-text\">\r\n")
			.append(
				"                            <div class=\"article-author-name\">" + board.getWriter() + "</div>\r\n")
			.append(
				"                            <div class=\"article-header-time\">\r\n")
			.append("                                " + board.getCreatedAt() + "\r\n")
			.append("                                <i class=\"icon-link\"></i>\r\n")
			.append("                            </div>\r\n")
			.append("                        </div>\r\n")
			.append("                    </div>\r\n")
			.append("                    <div class=\"article-doc\">\r\n");

		final String contents = board.getContents();
		final String[] token = contents.split("\n");
		for (String content : token) {
			sb.append("<p>"+ content +"</p>\r\n");
		}

		sb.append("                    </div>\r\n")
			.append("                </article>\r\n")
			.append("            </div>\r\n")
			.append("        </div>\r\n")
			.append("    </div>\r\n")
			.append("</div>\r\n");
	}

	private void createFooter(StringBuilder sb) {
		sb.append("<!-- script references -->\r\n")
			.append("<script src=\"../js/jquery-2.2.0.min.js\"></script>\r\n")
			.append("<script src=\"../js/bootstrap.min.js\"></script>\r\n")
			.append("<script src=\"../js/scripts.js\"></script>\r\n")
			.append("\t</body>\r\n")
			.append("</html>");
	}
}
