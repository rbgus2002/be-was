package was.webserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import was.controller.FrontController;
import was.controller.annotation.RequestMapping;
import was.webserver.response.HttpWasResponse;
import was.webserver.utils.HttpMethod;
import was.webserver.utils.HttpStatus;
import was.webserver.request.HttpWasRequest;
import was.webserver.utils.HttpMimeType;

public class WasHandler {

	private final HttpWasRequest httpWasRequest;
	private final HttpWasResponse httpWasResponse;
	private final FrontController frontController;
	public WasHandler(final HttpWasRequest httpWasRequest, final HttpWasResponse httpWasResponse, FrontController frontController) {
		this.httpWasRequest = httpWasRequest;
		this.httpWasResponse = httpWasResponse;
		this.frontController = frontController;
	}

	public void service() {
		try {
			if (responseDynamicFile()) {
				return;
			}
			final String resourcePath = httpWasRequest.getResourcePath();
			if (httpWasResponse.isExistResource(resourcePath)) {
				httpWasResponse.responseResource(resourcePath);
				return;
			}
			apiService(resourcePath);
		} catch (InvocationTargetException | IllegalAccessException e) {
			httpWasResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
			httpWasResponse.setBody(e.getCause().getMessage() , HttpMimeType.NOTING);
			httpWasResponse.doResponse();
		}
		
	}

	private void apiService(String resourcePath) throws InvocationTargetException, IllegalAccessException {
		final List<Method> methods = getResourcePathMethod(resourcePath);

		if (methods.isEmpty()) {
			httpWasResponse.setHttpStatus(HttpStatus.NOT_FOUND);
			httpWasResponse.setBody(HttpStatus.NOT_FOUND.getName(), HttpMimeType.PLAIN);
			return;
		}

		final String httpMethod = httpWasRequest.getHttpMethod();
		final Optional<Method> matchHttpMethod = getMatchHttpMethod(methods, httpMethod);
		if (matchHttpMethod.isPresent()) {
			runApiMethod(matchHttpMethod.get());
			return;
		}

		httpWasResponse.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
		httpWasResponse.setBody(HttpStatus.METHOD_NOT_ALLOWED.getName(), HttpMimeType.PLAIN);
	}

	private boolean responseDynamicFile() throws InvocationTargetException, IllegalAccessException {
		final String resourcePath = httpWasRequest.getResourcePath();
		final String[] token = resourcePath.split("\\.");

		final List<Method> methods = getResourcePathMethod(token[0]);

		if (methods.isEmpty())
			return false;

		final Optional<Method> matchHttpMethod = getMatchHttpMethod(methods, HttpMethod.GET.name());
		if (matchHttpMethod.isPresent()) {
			runApiMethod(matchHttpMethod.get());
			return true;
		}

		return false;
	}

	private void runApiMethod(final Method method) throws InvocationTargetException, IllegalAccessException {
		final Class<?> methodClass = method.getDeclaringClass();
		frontController.getInstance(methodClass.getName());
		method.invoke(frontController.getInstance(methodClass.getName()), httpWasRequest, httpWasResponse);
	}

	private List<Method> getResourcePathMethod(String resourcePath) {
		final Method[] declaredMethods = frontController.getDeclaredMethods();
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
