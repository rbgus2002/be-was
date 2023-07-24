package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotations.GetMapping;
import annotations.PostMapping;
import db.Database;
import http.HttpParameter;
import model.User;

public class Controller {
	private static Logger logger = LoggerFactory.getLogger(Controller.class);

	@GetMapping(path = "/")
	public String getIndex(HttpParameter httpParameter) {
		return "index.html";
	}

	@PostMapping(path = "/user/create")
	public String createUser(HttpParameter httpParameter) throws IllegalArgumentException {
		Database.addUser(parameterToUser(httpParameter));
		logger.debug("[Database] User {} added", Database.findUserById(httpParameter.getParameter("userId")).getName());
		return "redirect:/";
	}

	@PostMapping(path = "/user/login")
	public String login(HttpParameter httpParameter) {
		throw new UnsupportedOperationException();
	}

	private User parameterToUser(HttpParameter httpParameter) {
		return new User(
			httpParameter.getParameter("userId"),
			httpParameter.getParameter("password"),
			httpParameter.getParameter("name"),
			httpParameter.getParameter("email"));
	}
}
