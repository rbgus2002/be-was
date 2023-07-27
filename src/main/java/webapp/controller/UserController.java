package webapp.controller;

import webapp.db.Database;
import webapp.model.User;
import webserver.annotation.Controller;
import webserver.annotation.RequestBody;
import webserver.annotation.RequestMapping;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;
import webserver.session.Session;
import webserver.session.SessionStorage;

@Controller
public class UserController {

	@RequestMapping(method = HttpMethod.POST, path = "/user/create")
	public HttpResponse signUp(@RequestBody(name = "userId") String userId,
		@RequestBody(name = "password") String password,
		@RequestBody(name = "name") String name,
		@RequestBody(name = "email") String email) {
		User user = new User(userId, password, name, email);
		Database.addUser(user);
		return HttpResponse.builder()
			.status(HttpStatus.SEE_OTHER)
			.redirection("/index")
			.build();
	}

	@RequestMapping(method = HttpMethod.POST, path = "/user/login")
	public HttpResponse login(@RequestBody(name = "userId") String userId,
		@RequestBody(name = "password") String password) {
		User user = Database.findUserById(userId);
		if (user == null || !Database.verifyPassword(user, password)) {
			return HttpResponse.builder()
				.status(HttpStatus.UNAUTHORIZED)
				.view("/user/login_failed")
				.build();
		}
		// 세션 생성
		Session session = SessionStorage.createSession(userId);
		// 쿠키 저장
		return HttpResponse.builder()
			.status(HttpStatus.SEE_OTHER)
			.redirection("/index")
			.build();
	}
}
