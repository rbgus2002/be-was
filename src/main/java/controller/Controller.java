package controller;

import controller.annotation.RequestMapping;
import db.Database;
import model.User;
import webserver.HttpWasRequest;
import webserver.HttpWasResponse;
import webserver.utils.HttpMethod;

public class Controller {

	@RequestMapping(method = HttpMethod.GET, path = "/user/create")
	public void saveUser(HttpWasRequest request, HttpWasResponse response) {
		final String userId = request.getParameter("userId");
		final String password = request.getParameter("password");
		final String name = request.getParameter("name");
		final String email = request.getParameter("email");
		final User user = new User(userId, password, name, email);

		Database.addUser(user);

		response.response302Header("http://localhost:8080/index.html");
	}
}
