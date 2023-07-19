package controllers;

import webserver.GetMapping;

public class Controller {

	@GetMapping("/")
	public String getIndex() {
		return "index.html";
	}
}
