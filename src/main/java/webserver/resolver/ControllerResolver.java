package webserver.resolver;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import webserver.annotation.RequestBody;
import webserver.annotation.RequestParam;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;
import webserver.mapping.ControllerMapping;
import webserver.mapping.UrlMapping;

public class ControllerResolver {

	private final UrlMapping urlMapping = UrlMapping.getInstance();
	private final ControllerMapping controllerMapping = ControllerMapping.getInstance();

	public HttpResponse resolve(HttpRequest request) throws ReflectiveOperationException {
		Method method = urlMapping.getMethod(request.getHttpMethod(), request.getUrlPath());
		if (method == null) {
			return HttpResponse.builder()
				.status(HttpStatus.NOT_FOUND)
				.build();
		}
		return invoke(request, method);
	}

	private HttpResponse invoke(HttpRequest request, Method method) throws ReflectiveOperationException {
		Object controller = controllerMapping.getControllerByMethod(method);
		Parameter[] parameters = method.getParameters();
		if (parameters.length == 0) {
			return (HttpResponse)method.invoke(controller);
		}

		List<String> args = resolveArguments(request, parameters);
		if (args.isEmpty() || args.contains(null)) {
			return HttpResponse.builder()
				.status(HttpStatus.BAD_REQUEST)
				.build();
		}
		return (HttpResponse)method.invoke(controller, args.toArray());
	}

	private List<String> resolveArguments(HttpRequest request, Parameter[] parameters) {
		if (request.isMethodGet()) {
			return getRequestParams(request, parameters);
		}
		if (request.isMethodPost()) {
			List<String> requestBody = getRequestBody(request, parameters);
			return requestBody;
		}
		return Collections.emptyList();
	}

	private List<String> getRequestParams(HttpRequest request, Parameter[] parameters) {
		return Arrays.stream(parameters)
			.map(parameter -> parameter.getAnnotation(RequestParam.class))
			.map(requestParam -> request.getUrlParamValue(requestParam.name()))
			.collect(Collectors.toList());
	}

	private List<String> getRequestBody(HttpRequest request, Parameter[] parameters) {
		return Arrays.stream(parameters)
			.map(parameter -> parameter.getAnnotation(RequestBody.class))
			.map(requestBody -> request.getBodyValue(requestBody.name()))
			.collect(Collectors.toList());
	}

}
