package controller;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;

public class HomeController implements Controller {

	@Override
	public String process(HttpRequest request, HttpResponse response) {
		return "index";
	}
}
