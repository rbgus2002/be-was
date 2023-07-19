package controllers;

import annotations.GetMapping;
import annotations.PostMapping;
import annotations.RequestParam;

public class Controller {

	@GetMapping(path = "/")
	public String getIndex() {
		return "index.html";
	}

	@GetMapping(path = "/create")
	public String createUser(@RequestParam String userId, @RequestParam String password, @RequestParam String name, @RequestParam String email) {
		return "";
	}
}
