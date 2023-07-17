package controller;

import controller.annotation.RequestMapping;
import service.UserService;
import webserver.HttpWasRequest;
import webserver.HttpWasResponse;
import webserver.utils.HttpMethod;

public class Controller {

	private final UserService userService;

	public Controller(final UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = HttpMethod.GET, path = "/user/create")
	public void saveUser(HttpWasRequest request, HttpWasResponse response) {
		userService.saveUser(request);
		response.response302Header("http://localhost:8080/index.html");
	}
}
