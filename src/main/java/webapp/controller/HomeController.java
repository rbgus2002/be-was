package webapp.controller;

import webapp.db.Database;
import webapp.model.User;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

@Controller
public class HomeController {

	@RequestMapping(method = HttpMethod.GET, path = "/index")
	public String home() {
		return "index.html";
	}

	@RequestMapping(method = HttpMethod.GET, path = "/user/create")
	public String join(HttpRequest request, HttpResponse response) {
		String userId = request.getParam("userId");
		String password = request.getParam("password");
		String name = request.getParam("name");
		String email = request.getParam("email");
		User user = new User(userId, password, name, email);
		Database.addUser(user);
		response.setStatus(HttpStatus.CREATED);
		return "/user/login.html";
	}
}
