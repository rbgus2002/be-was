package webapp.controller;

import webapp.model.User;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpResponse;
import webserver.view.View;

@Controller
public class HomeController {

	@RequestMapping(method = HttpMethod.GET, path = "/index")
	public HttpResponse home(User user) {
		View indexView = View.of("index", user);
		return HttpResponse.builder()
			.view(indexView)
			.build();
	}

}
