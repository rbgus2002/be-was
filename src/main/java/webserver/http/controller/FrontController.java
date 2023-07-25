package webserver.http.controller;

import java.io.File;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.utils.FileMapper;
import webserver.mapping.ControllerScanner;

public class FrontController {

	FileMapper fileMapper = new FileMapper();
	StaticFileResolver staticFileResolver = new StaticFileResolver();
	ControllerResolver controllerResolver = new ControllerResolver();

	public void service(HttpRequest request, HttpResponse response) {
		String path = request.getUrlPath();
		ControllerScanner.initialize();
		File file = fileMapper.findFile(path);
		if (file != null) {
			staticFileResolver.resolve(response, file);
			return;
		}
		controllerResolver.resolve(request, response);
	}

}
