package webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import controller.Controller;
import controller.annotation.RequestMapping;

public class WasHandler {

	private final HttpWasRequest httpWasRequest;
	private final HttpWasResponse httpWasResponse;
	private final Controller controller;
	public WasHandler(final HttpWasRequest httpWasRequest, final HttpWasResponse httpWasResponse, Controller controller) {
		this.httpWasRequest = httpWasRequest;
		this.httpWasResponse = httpWasResponse;
		this.controller = controller;
	}

	public void service() throws InvocationTargetException, IllegalAccessException {
		final List<Method> methods = getResourcePathMethod();

		if (methods.isEmpty()) {
			final String resourcePath = httpWasRequest.getResourcePath();
			httpWasResponse.responseResource(resourcePath);
			return;
		}

		final Method method = methods.get(0);
		method.invoke(controller, httpWasRequest, httpWasResponse);
	}

	private List<Method> getResourcePathMethod() {
		final String resourcePath = httpWasRequest.getResourcePath();

		final Method[] declaredMethods = Controller.class.getDeclaredMethods();
		return Arrays.stream(declaredMethods)
			.filter(method -> method.isAnnotationPresent(RequestMapping.class))
			.filter(method -> {
				final RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
				final String path = requestMapping.path();
				return path.equals(resourcePath);
			})
			.collect(Collectors.toList());
	}
}
