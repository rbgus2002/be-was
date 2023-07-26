package webapp.controller;

import webapp.db.Database;
import webapp.model.User;
import webserver.annotation.Controller;
import webserver.annotation.RequestBody;
import webserver.annotation.RequestMapping;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

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
			.status(HttpStatus.FOUND)
			.redirection("/index")
			.build();
	}
}
