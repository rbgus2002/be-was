package controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotations.GetMapping;
import annotations.PostMapping;
import db.Database;
import service.LoginService;
import webserver.http.HttpParameter;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import model.User;
import webserver.session.Session;
import webserver.session.SessionConst;

public class Controller {
	private static Logger logger = LoggerFactory.getLogger(Controller.class);

	@GetMapping(path = "/")
	public String getIndex(HttpRequest httpRequest, HttpResponse httpResponse) {
		return "index.html";
	}

	@GetMapping(path = "/qna/show.html")
	public String getQna(HttpRequest httpRequest, HttpResponse httpResponse) {
		try {
			LoginService.checkSession(httpRequest.getCookieValue(SessionConst.sessionId));
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return "redirect:/user/login.html";
		}
		return "/qna/show.html";
	}

	@PostMapping(path = "/user/create")
	public String createUser(HttpRequest httpRequest, HttpResponse httpResponse) throws IllegalArgumentException {
		try {
			Database.addUser(parameterToUser(httpRequest.getParameter()));
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
			return "redirect:/user/form.html";
		}
		httpResponse.addCookie(SessionConst.sessionId, Session.newInstance().createSession(httpRequest.getParameter().getParameter("userId")));

		return "redirect:/";
	}

	@PostMapping(path = "/user/login")
	public String login(HttpRequest httpRequest, HttpResponse httpResponse) {
		try {
			String userId = httpRequest.getParameter().getParameter("userId");
			String password = httpRequest.getParameter().getParameter("password");
			LoginService.login(userId, password);
			logger.debug("{} LOGIN 성공", userId);
			httpResponse.addCookie(SessionConst.sessionId, Session.newInstance().createSession(userId));
			return "redirect:/";
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
		}
		return "user/login_failed.html";
	}

	private User parameterToUser(HttpParameter httpParameter) {
		return new User(httpParameter.getParameter("userId"), httpParameter.getParameter("password"),
			httpParameter.getParameter("name"), httpParameter.getParameter("email"));
	}

}
