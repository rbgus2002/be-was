package webapp.controller;

import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.http.message.HttpMethod;

@Controller
public class HomeController {

	@RequestMapping(method = HttpMethod.GET, path = "/index")
	public String home() {
		return "index";
	}

}
