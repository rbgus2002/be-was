package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import webserver.view.ModelView;

public class Controller {
	private static Logger logger = LoggerFactory.getLogger(Controller.class);

	@GetMapping(path = "/")
	public ModelView getMainIndex(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		modelView = reflectLogin(httpRequest, modelView);
		return modelView.setPath("index.html");
	}

	@GetMapping(path = "/index.html")
	public ModelView getIndex(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		modelView = reflectLogin(httpRequest, modelView);
		return modelView.setPath("index.html");
	}

	@GetMapping(path = "/qna/form.html")
	public ModelView qnaFormPage(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		modelView = reflectLogin(httpRequest, modelView);
		return modelView.setPath("qna/form.html");
	}

	@GetMapping(path = "/qna/show.html")
	public ModelView getQna(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		modelView = reflectLogin(httpRequest, modelView);
		if (isLoggedIn(modelView)) {
			return modelView.setPath("qna/show.html");
		}
		return modelView.setPath("redirect:/user/login.html");
	}

	@GetMapping(path = "/user/form.html")
	public ModelView userFormPage(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		modelView = reflectLogin(httpRequest, modelView);
		return modelView.setPath("user/form.html");
	}

	@PostMapping(path = "/user/create")
	public ModelView createUser(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) throws
		IllegalArgumentException {
		modelView = reflectLogin(httpRequest, modelView);
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
		modelView = reflectLogin(httpRequest, modelView);
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

	@GetMapping(path = "/user/login.html")
	public ModelView loginPage(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		modelView = reflectLogin(httpRequest, modelView);
		return modelView.setPath("user/login.html");
	}

	@GetMapping(path = "/user/logout")
	public ModelView logout(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		logoutSession(httpRequest);
		return modelView.setPath("redirect:/");
	}

	@GetMapping(path = "/user/list.html")
	public ModelView listPage(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		modelView = reflectLogin(httpRequest, modelView);
		if (isLoggedIn(modelView)) {
			List<Map<String, String>> userStats = new ArrayList<>();
			Map<String, String> userStat;
			for (User user : Database.getUserList()) {
				userStat = new HashMap<>();
				userStat.put("userId", user.getUserId());
				userStat.put("name", user.getName());
				userStat.put("email", user.getEmail());
				userStats.add(userStat);
			}
			modelView.addAttribute("userStats", userStats);
			return modelView.setPath("user/list.html");
		}
		return modelView.setPath("redirect:/user/login.html");
	}

	@GetMapping(path = "/user/profile.html")
	public ModelView userProfilePage(HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		modelView = reflectLogin(httpRequest, modelView);
		return modelView.setPath("user/profile.html");
	}

	private User parameterToUser(HttpParameter httpParameter) {
		return new User(httpParameter.getParameter("userId"), httpParameter.getParameter("password"),
			httpParameter.getParameter("name"), httpParameter.getParameter("email"));
	}

	private ModelView reflectLogin(HttpRequest httpRequest, ModelView modelView) {
		try {
			if (LoginService.checkSession(httpRequest.getCookieValue(SessionConst.sessionId))) {
				String userId = LoginService.getUserIdFrom(httpRequest.getCookieValue(SessionConst.sessionId));
				Database.findUserById(userId).getName();
				// 인증 성공
				modelView.addAttribute("login", "true");
			}
		} catch (Exception e) {
		}
		return modelView;
	}

	private void logoutSession(HttpRequest httpRequest) {
		try {
			if (LoginService.checkSession(httpRequest.getCookieValue(SessionConst.sessionId))) {
				Session.getInstance().removeSession(httpRequest.getCookieValue(SessionConst.sessionId));
			}
		} catch (Exception e) {
		}
	}

	private boolean isLoggedIn(ModelView modelView) {
		return (modelView.containsAttribute("login")) && (modelView.getAttribute("login").equals("true"));
	}

}
