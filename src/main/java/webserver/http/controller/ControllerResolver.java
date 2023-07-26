package webserver.http.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import webserver.annotation.RequestParam;
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
			fileName = invoke(method, controller, request, response);
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

	private String invoke(Method method, Object controller, HttpRequest request, HttpResponse response) throws
		InvocationTargetException,
		IllegalAccessException {
		Parameter[] parameters = method.getParameters();
		List<Object> args = Arrays.stream(parameters)
			.filter(parameter -> parameter.isAnnotationPresent(RequestParam.class))
			.map(parameter -> parameter.getAnnotation(RequestParam.class))
			.map(requestParam -> request.getParam(requestParam.name()))
			.collect(Collectors.toList());

		if (args.contains(null)) {
			throw new IllegalArgumentException();
		}
		args.add(response);
		return (String)method.invoke(controller, args.toArray());
	}

}
