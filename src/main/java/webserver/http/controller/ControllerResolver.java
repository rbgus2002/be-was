package webserver.http.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;
import webserver.http.utils.FileMapper;
import webserver.mapping.ControllerMapping;
import webserver.mapping.UrlMapping;

public class ControllerResolver {

	private final UrlMapping urlMapping = UrlMapping.getInstance();
	private final ControllerMapping controllerMapping = ControllerMapping.getInstance();
	private final FileMapper fileMapper = new FileMapper();

	public void resolve(HttpRequest request, HttpResponse response) {
		Method method = urlMapping.find(request.getUrlPath(), request.getHttpMethod());
		if (method == null) {
			setBadRequest(response);
			return;
		}

		Object controller = controllerMapping.find(method.getDeclaringClass());
		if (controller == null) {
			setBadRequest(response);
			return;
		}

		String fileName = null;
		try {
			fileName = (String)method.invoke(controller, request, response);
			File file = fileMapper.findFile(fileName);
			response.setBody(Files.readAllBytes(file.toPath()), Files.probeContentType(file.toPath()));
		} catch (IllegalAccessException | InvocationTargetException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void setBadRequest(HttpResponse response) {
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setBody("test".getBytes(), "text/plain");
	}

}
