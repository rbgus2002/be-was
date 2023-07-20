package webapp.controller;

import webserver.annotation.Controller;
import webserver.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(path = "/index.html")
	public String home() {
		return "index";
	}

}
