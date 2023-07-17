package service;

import db.Database;
import model.User;
import webserver.HttpWasRequest;

public class UserService {

	public void saveUser(HttpWasRequest request) {
		final String userId = request.getParameter("userId");
		final String password = request.getParameter("password");
		final String name = request.getParameter("name");
		final String email = request.getParameter("email");
		final User user = new User(userId, password, name, email);

		Database.addUser(user);
	}
}
