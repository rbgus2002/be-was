package webapp.controller;

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
	public String home(HttpResponse response) {
		response.setStatus(HttpStatus.OK);
		return "index.html";
	}

	@RequestMapping(method = HttpMethod.GET, path = "/user/create")
	public String signUp(@RequestParam(name = "userId") String userId,
		@RequestParam(name = "password") String password,
		@RequestParam(name = "name") String name,
		@RequestParam(name = "email") String email,
		HttpResponse response) {
		User user = new User(userId, password, name, email);
		Database.addUser(user);
		response.setStatus(HttpStatus.CREATED);
		return "/user/login.html";
	}
}
