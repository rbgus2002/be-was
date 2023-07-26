package controllers;

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
import webserver.view.Model;
import webserver.view.ModelView;

public class Controller {
	private static Logger logger = LoggerFactory.getLogger(Controller.class);

	@GetMapping(path = "/")
	public ModelView getMainIndex(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		try {
			if (LoginService.checkSession(httpRequest.getCookieValue(SessionConst.sessionId))) {
				// 인증 성공
				String userId = LoginService.getUserIdFrom(httpRequest.getCookieValue(SessionConst.sessionId));
				Database.findUserById(userId).getName();

				modelView.addAttribute("login", "true");

				return modelView.setPath("index.html");
			}
		} catch (Exception e) {
		}
		// 인증 실패
		return modelView.setPath("index.html");
	}

	@GetMapping(path = "/index.html")
	public ModelView getIndex(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		try {
			if (LoginService.checkSession(httpRequest.getCookieValue(SessionConst.sessionId))) {
				// 인증 성공
				String userId = LoginService.getUserIdFrom(httpRequest.getCookieValue(SessionConst.sessionId));
				Database.findUserById(userId).getName();

				modelView.addAttribute("login", "true");

				return modelView.setPath("index.html");
			}
		} catch (Exception e) {
		}
		// 인증 실패
		return modelView.setPath("index.html");
	}

	@GetMapping(path = "/qna/show.html")
	public ModelView getQna(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		if (LoginService.checkSession(httpRequest.getCookieValue(SessionConst.sessionId))) {
			// 인증 성공
			return modelView.setPath("/qna/show.html");
		}
		// 인증 실패
		return modelView.setPath("redirect:/user/login.html");
	}

	@PostMapping(path = "/user/create")
	public ModelView createUser(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) throws
		IllegalArgumentException {
		try {
			Database.addUser(parameterToUser(httpRequest.getParameter()));
		} catch (IllegalArgumentException e) {
			// 회원가입 실패
			logger.debug(e.getMessage());
			return modelView.setPath("redirect:/user/form.html");
		}
		// 회원가입 성공
		httpResponse.addCookie(SessionConst.sessionId, Session.getInstance().createSession(httpRequest.getParameter().getParameter("userId")));

		return modelView.setPath("redirect:/");
	}

	@PostMapping(path = "/user/login")
	public ModelView login(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		String userId = httpRequest.getParameter().getParameter("userId");
		String password = httpRequest.getParameter().getParameter("password");
		if (LoginService.login(userId, password)) {
			// 로그인 성공
			httpResponse.addCookie(SessionConst.sessionId, Session.getInstance().createSession(userId));
			return modelView.setPath("redirect:/");
		}
		// 로그인 실패
		return modelView.setPath("user/login_failed.html");
	}

	private User parameterToUser(HttpParameter httpParameter) {
		return new User(httpParameter.getParameter("userId"), httpParameter.getParameter("password"),
			httpParameter.getParameter("name"), httpParameter.getParameter("email"));
	}

}
