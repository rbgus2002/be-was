package webserver.http.controller;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;

public class FrontController {

	StaticFileResolver staticFileResolver;
	ControllerResolver controllerResolver;

	private FrontController() {
		staticFileResolver = new StaticFileResolver();
		controllerResolver = new ControllerResolver();
	}

	public static FrontController getInstance() {
		return LazyHolder.instance;
	}

	public HttpResponse service(HttpRequest request) {
		try {
			if (request.forStaticResource()) {
				return staticFileResolver.resolve(request.getUrlPath());
			}
			return controllerResolver.resolve(request);
		} catch (Exception e) {
			return HttpResponse.builder()
				.status(HttpStatus.SERVER_ERROR)
				.build();
		}
	}

	private static class LazyHolder {
		private static final FrontController instance = new FrontController();
	}

}
