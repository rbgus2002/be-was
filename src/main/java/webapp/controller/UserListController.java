package webapp.controller;

import webserver.annotation.Authenticated;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.http.message.HttpMethod;
import webserver.http.message.HttpResponse;
import webserver.view.View;

@Controller
public class UserListController {

	@Authenticated
	@RequestMapping(method = HttpMethod.GET, path = "/user/list")
	public HttpResponse userList() {
		View listView = View.of("user/list");
		return HttpResponse.builder()
			.view(listView)
			.build();
	}
}
