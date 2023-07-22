package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotations.GetMapping;
import db.Database;
import http.Parameter;
import model.User;

public class Controller {
	private static Logger logger = LoggerFactory.getLogger(Controller.class);

	@GetMapping(path = "/")
	public String getIndex(Parameter parameter) {
		return "index.html";
	}

	@GetMapping(path = "/create")
	public String createUser(Parameter parameter) throws IllegalArgumentException {
		Database.addUser(parameterToUser(parameter));
		logger.debug("[Database] User {} added", Database.findUserById(parameter.getParameter("userId")).getName());
		return "redirect:http://localhost:8080/";
	}

	private User parameterToUser(Parameter parameter) {
		return new User(
			parameter.getParameter("userId"),
			parameter.getParameter("password"),
			parameter.getParameter("name"),
			parameter.getParameter("email"));
	}
}
