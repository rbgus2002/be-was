package webserver.http.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import webserver.container.ControllerMapping;
import webserver.container.UrlMapping;
import webserver.http.message.HttpMethod;

public class ControllerResolver {

	private final UrlMapping urlMapping = UrlMapping.getInstance();
	private final ControllerMapping controllerMapping = ControllerMapping.getInstance();

	public String resolve(String path, HttpMethod httpMethod) {
		try {
			Method method = urlMapping.find(path, httpMethod);
			Object controller = controllerMapping.find(method.getDeclaringClass());
			return (String)method.invoke(controller);
		} catch (NullPointerException | IllegalArgumentException | IllegalAccessException |
				 InvocationTargetException e) {
			return null;
		}
	}
}
