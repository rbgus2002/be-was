package controller;

import controller.annotation.RequestMapping;
import db.Database;
import model.User;
import webserver.request.HttpWasRequest;
import webserver.response.HttpWasResponse;
import webserver.session.Cookie;
import webserver.session.HttpSession;
import webserver.utils.HttpHeader;
import webserver.utils.HttpMethod;
import webserver.utils.HttpStatus;

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
			response.responseResource("/user/login_failed.html");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
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
}
