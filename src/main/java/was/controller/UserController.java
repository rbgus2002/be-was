package was.controller;

import was.webserver.annotation.Controller;
import was.controller.annotation.RequestMapping;
import was.db.Database;
import was.model.User;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;
import was.webserver.session.Cookie;
import was.webserver.session.HttpSession;
import was.webserver.utils.HttpHeader;
import was.webserver.utils.HttpMethod;
import was.webserver.utils.HttpStatus;

@Controller
public class UserController {

	@RequestMapping(method = HttpMethod.POST, path = "/user/create")
	public void saveUser(HttpWasRequest request, HttpWasResponse response) {
		final String userId = request.getParameter("userId");
		final String password = request.getParameter("password");
		final String name = request.getParameter("name");
		final String email = request.getParameter("email");
		final User user = new User(userId, password, name, email);
		Database.addUser(user);

		response.setHttpStatus(HttpStatus.FOUND);
		response.addHeader(HttpHeader.LOCATION, "http://localhost:8080/index.html");
	}

	@RequestMapping(method = HttpMethod.POST, path = "/user/login")
	public void loginUser(HttpWasRequest request, HttpWasResponse response) {
		final String userId = request.getParameter("userId");
		final String password = request.getParameter("password");

		final User findUser = Database.findUserById(userId);

		if (findUser == null || !findUser.getPassword().equals(password)) {
			response.responseResource("/user/login_failed.html", HttpStatus.BAD_REQUEST);
			return;
		}

		final HttpSession httpSession = HttpSession.getInstance();
		final String sessionId = httpSession.createSession(userId);

		final Cookie cookie = new Cookie.Builder(HttpSession.SESSION_ID, sessionId)
			.path("/")
			.httpOnly(true)
			.maxAge(3600L)
			.build();

		response.addCookie(cookie);
		response.setHttpStatus(HttpStatus.FOUND);
		response.addHeader(HttpHeader.LOCATION, "http://localhost:8080/index.html");
	}

	@RequestMapping(method = HttpMethod.GET, path = "/logout")
	public void logout(HttpWasRequest request, HttpWasResponse response) {
		final String sessionId = request.getSessionId();
		HttpSession httpSession = HttpSession.getInstance();

		httpSession.expiredSession(sessionId);

		final Cookie cookie = new Cookie.Builder(HttpSession.SESSION_ID, "")
			.maxAge(0L)
			.build();

		response.addCookie(cookie);
		response.setHttpStatus(HttpStatus.FOUND);
		response.addHeader(HttpHeader.LOCATION, "http://localhost:8080/index.html");
	}
}
