package webapp.controller;

import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpResponse;

@Controller
public class HomeController {

	@RequestMapping(method = HttpMethod.GET, path = "/index")
	public HttpResponse home() {
		return HttpResponse.builder()
			.view("/index")
			.build();
	}

}
