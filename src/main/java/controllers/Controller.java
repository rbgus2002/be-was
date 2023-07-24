package controllers;

import java.util.NoSuchElementException;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotations.GetMapping;
import annotations.PostMapping;
import db.Database;
import http.HttpParameter;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import session.Session;
import session.SessionConst;

public class Controller {
	private static Logger logger = LoggerFactory.getLogger(Controller.class);

	@GetMapping(path = "/")
	public String getIndex(HttpRequest httpRequest, HttpResponse httpResponse) {
		return "index.html";
	}

	@PostMapping(path = "/user/create")
	public String createUser(HttpRequest httpRequest, HttpResponse httpResponse) throws IllegalArgumentException {
		Database.addUser(parameterToUser(httpRequest.getParameter()));
		logger.debug("[Database] User {} added",
			Database.findUserById(httpRequest.getParameter().getParameter("userId")).getName());
		return "redirect:/";
	}

	@PostMapping(path = "/user/login")
	public String login(HttpRequest httpRequest, HttpResponse httpResponse) {

		try {
			String userId = httpRequest.getParameter().getParameter("userId");
			String password = httpRequest.getParameter().getParameter("password");
			if (Database.verifyUser(userId, password)) {
				logger.debug("{} LOGIN 성공", userId);
				httpResponse.addCookie(SessionConst.sessionId, Session.newInstance().createSession(userId));
				return "redirect:/";
			}
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
		}
		return "user/login_failed.html";

		// Session session = Session.newInstance();
		// try {
		// 	String userId = session.getUserId(httpRequest.getCookieValue(SessionConst.sessionId));
		// 	User user = Database.findUserById(userId);
		// 	logger.debug("{} LOGIN 성공", user.getUserId());
		// } catch (IllegalArgumentException | NoSuchElementException e) {
		// 	logger.debug(e.getMessage());
		// 	return "user/login_failed.html";
		// }
	}

	private User parameterToUser(HttpParameter httpParameter) {
		return new User(httpParameter.getParameter("userId"), httpParameter.getParameter("password"),
			httpParameter.getParameter("name"), httpParameter.getParameter("email"));
	}
}
