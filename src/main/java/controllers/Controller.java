package controllers;

import annotations.GetMapping;

public class Controller {

	@GetMapping(path = "/")
	public String getIndex() {
		return "index.html";
	}
}
