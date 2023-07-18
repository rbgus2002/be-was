package webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import controller.Controller;
import controller.FrontController;
import controller.annotation.RequestMapping;
import webserver.request.HttpWasRequest;
import webserver.response.HttpWasResponse;
import webserver.utils.HttpMethod;

public class WasHandler {

	private final HttpWasRequest httpWasRequest;
	private final HttpWasResponse httpWasResponse;
	private final FrontController frontController;
	public WasHandler(final HttpWasRequest httpWasRequest, final HttpWasResponse httpWasResponse, FrontController frontController) {
		this.httpWasRequest = httpWasRequest;
		this.httpWasResponse = httpWasResponse;
		this.frontController = frontController;
	}

	public void service() throws InvocationTargetException, IllegalAccessException {
		final List<Method> methods = getResourcePathMethod();

		if (methods.isEmpty()) {
			final String resourcePath = httpWasRequest.getResourcePath();
			httpWasResponse.responseResource(resourcePath);
			return;
		}

		final String httpMethod = httpWasRequest.getHttpMethod();
		final Optional<Method> matchHttpMethod = getMatchHttpMethod(methods, httpMethod);
		if (matchHttpMethod.isEmpty()) {
			httpWasResponse.response405();
			return;
		}

		final Method method = matchHttpMethod.get();

		final Class<?> methodClass = method.getDeclaringClass();
		frontController.getInstance(methodClass.getName());
		method.invoke(frontController.getInstance(methodClass.getName()), httpWasRequest, httpWasResponse);
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

	private Optional<Method> getMatchHttpMethod(List<Method> methods, String inputMethod) {
		return methods.stream()
			.filter(method -> {
				final RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
				final HttpMethod httpMethod = requestMapping.method();
				return httpMethod == HttpMethod.valueOf(inputMethod);
			})
			.findAny();
	}
}
