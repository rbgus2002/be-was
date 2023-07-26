package webapp.controller;

import java.io.IOException;

import webapp.db.Database;
import webapp.model.User;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.annotation.RequestParam;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

@Controller
public class HomeController {

	@RequestMapping(method = HttpMethod.GET, path = "/index")
	public HttpResponse home() throws IOException {
		return HttpResponse.builder()
			.view("/index")
			.build();
	}

	@RequestMapping(method = HttpMethod.GET, path = "/user/create")
	public HttpResponse signUp(@RequestParam(name = "userId") String userId,
		@RequestParam(name = "password") String password,
		@RequestParam(name = "name") String name,
		@RequestParam(name = "email") String email) {
		User user = new User(userId, password, name, email);
		Database.addUser(user);
		return HttpResponse.builder()
			.status(HttpStatus.CREATED)
			.view("/user/login")
			.build();
	}
}
