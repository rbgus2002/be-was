package webserver.http.controller;

import java.io.IOException;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;

public class FrontController {

	StaticFileResolver staticFileResolver = new StaticFileResolver();
	// ControllerResolver controllerResolver = new ControllerResolver();

	public HttpResponse service(HttpRequest request) throws IOException {
		if (request.forStaticResource()) {
			return staticFileResolver.resolve(request.getUrlPath());
		}
		// controllerResolver.resolve(request, response);
		return null;
	}

}
