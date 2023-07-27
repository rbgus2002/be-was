package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotations.GetMapping;
import annotations.PostMapping;
import db.ArticleDatabase;
import db.UserDatabase;
import model.Article;
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
	public ModelView getMainIndex(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		return modelView.setPath("redirect:index.html");
	}

	@GetMapping(path = "/index.html")
	public ModelView getIndex(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		reflectLogin(httpRequest, modelView);
		List<Article> articles = ArticleDatabase.getArticles();
		if (!articles.isEmpty()) {
			List<Map<String, String>> articleStats = new ArrayList<>();
			Map<String, String> articleStat;
			for (Article article : articles) {
				articleStat = new HashMap<>();
				articleStat.put("title", article.getTitle());
				articleStat.put("writer", article.getWriter());
				articleStat.put("content", article.getContents());
				articleStats.add(articleStat);
			}
			modelView.addAttribute("articles", articleStats);
		}
		return modelView.setPath("index.html");
	}

	@GetMapping(path = "/qna/form.html")
	public ModelView qnaFormPage(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		reflectLogin(httpRequest, modelView);
		if (isLoggedIn(modelView)) {
			return modelView.setPath("qna/form.html");
		}
		return modelView.setPath("redirect:/user/login.html");
	}

	@PostMapping(path = "/qna/form")
	public ModelView submitArticleForm(final HttpRequest httpRequest, HttpResponse httpResponse,
		ModelView modelView) throws IllegalArgumentException {
		try {
			String title = httpRequest.getParameter().getParameter("title");
			String writer = httpRequest.getParameter().getParameter("writer");
			String contents = httpRequest.getParameter().getParameter("contents");
			ArticleDatabase.addArticle(Article.of(title, writer, contents));
			return modelView.setPath("redirect:/");
		} catch (Exception e) {
			throw new IllegalArgumentException("게시물 등록이 실패했습니다.");
		}
	}

	@GetMapping(path = "/qna/show.html")
	public ModelView showArticlePage(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		reflectLogin(httpRequest, modelView);
		if (isLoggedIn(modelView)) {
			int index = Integer.parseInt(httpRequest.getParameter().getParameter("index"));
			Article article = ArticleDatabase.getArticleAt(index - 1);
			modelView.addAttribute("title", article.getTitle());
			modelView.addAttribute("writer", article.getWriter());
			modelView.addAttribute("contents", article.getContents());
			return modelView.setPath("qna/show.html");
		}
		return modelView.setPath("redirect:/user/login.html");
	}

	@GetMapping(path = "/user/form.html")
	public ModelView userFormPage(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		reflectLogin(httpRequest, modelView);
		return modelView.setPath("user/form.html");
	}

	@PostMapping(path = "/user/create")
	public ModelView createUser(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) throws
		IllegalArgumentException {
		reflectLogin(httpRequest, modelView);
		try {
			UserDatabase.addUser(parameterToUser(httpRequest.getParameter()));
		} catch (IllegalArgumentException e) {
			// 회원가입 실패
			logger.debug(e.getMessage());
			return modelView.setPath("redirect:/user/form.html");
		}
		// 회원가입 성공
		httpResponse.addCookie(SessionConst.sessionId,
			Session.getInstance().createSession(httpRequest.getParameter().getParameter("userId")));

		return modelView.setPath("redirect:/");
	}

	@PostMapping(path = "/user/login")
	public ModelView login(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		reflectLogin(httpRequest, modelView);
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
	public ModelView loginPage(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		reflectLogin(httpRequest, modelView);
		return modelView.setPath("user/login.html");
	}

	@GetMapping(path = "/user/logout")
	public ModelView logout(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		logoutSession(httpRequest);
		return modelView.setPath("redirect:/");
	}

	@GetMapping(path = "/user/list.html")
	public ModelView listPage(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		reflectLogin(httpRequest, modelView);
		if (isLoggedIn(modelView)) {
			List<Map<String, String>> userStats = new ArrayList<>();
			Map<String, String> userStat;
			for (User user : UserDatabase.getUserList()) {
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
	public ModelView userProfilePage(final HttpRequest httpRequest, HttpResponse httpResponse, ModelView modelView) {
		reflectLogin(httpRequest, modelView);
		return modelView.setPath("user/profile.html");
	}

	private User parameterToUser(final HttpParameter httpParameter) {
		String userId = httpParameter.getParameter("userId");
		String password = httpParameter.getParameter("password");
		String name = httpParameter.getParameter("name");
		String email = httpParameter.getParameter("email");
		return User.of(userId, password, name, email);
	}

	private ModelView reflectLogin(final HttpRequest httpRequest, ModelView modelView) {
		try {
			if (LoginService.checkSession(httpRequest.getCookieValue(SessionConst.sessionId))) {
				String userId = LoginService.getUserIdFrom(httpRequest.getCookieValue(SessionConst.sessionId));
				// 인증 성공
				modelView.addAttribute("name", UserDatabase.findUserById(userId).getName());
				modelView.addAttribute("login", "true");
			}
		} catch (Exception e) {
		}
		return modelView;
	}

	private void logoutSession(final HttpRequest httpRequest) {
		try {
			if (LoginService.checkSession(httpRequest.getCookieValue(SessionConst.sessionId))) {
				Session.getInstance().removeSession(httpRequest.getCookieValue(SessionConst.sessionId));
			}
		} catch (Exception e) {
		}
	}

	private boolean isLoggedIn(final ModelView modelView) {
		return (modelView.containsAttribute("login")) && (modelView.getAttribute("login").equals("true"));
	}

}
