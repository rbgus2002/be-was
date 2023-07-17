package webserver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import controller.Controller;
import controller.annotation.RequestMapping;

public class WasHandler {

	private final HttpWasRequest httpWasRequest;
	private final HttpWasResponse httpWasResponse;

	public WasHandler(final HttpWasRequest httpWasRequest, final HttpWasResponse httpWasResponse) {
		this.httpWasRequest = httpWasRequest;
		this.httpWasResponse = httpWasResponse;
	}

	public void service() throws IOException {
		final List<Method> methods = getResourcePathMethod();

		if (methods.isEmpty()) {
			final String resourcePath = httpWasRequest.getResourcePath();
			httpWasResponse.responseResource(resourcePath);
			return;
		}
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
