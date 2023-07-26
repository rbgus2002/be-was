package webserver.http.controller;

import java.lang.invoke.MethodHandle;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import webserver.annotation.RequestParam;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;
import webserver.mapping.UrlMapping;

public class ControllerResolver {

	private final UrlMapping urlMapping = UrlMapping.getInstance();

	public HttpResponse resolve(HttpRequest request) throws Throwable {
		MethodHandle methodHandle = urlMapping.getMethodHandle(request.getHttpMethod(), request.getUrlPath());
		if (methodHandle == null) {
			return HttpResponse.builder()
				.status(HttpStatus.NOT_FOUND)
				.build();
		}
		return invoke(request, methodHandle);
	}

	private HttpResponse invoke(HttpRequest request, MethodHandle methodHandle) throws Throwable {
		List<Class<?>> parameterList = methodHandle.type().parameterList();
		if (parameterList.isEmpty()) {
			return (HttpResponse)methodHandle.invoke();
		}

		List<String> args = resolveArguments(request, parameterList);
		if (args.isEmpty() || args.contains(null)) {
			return HttpResponse.builder()
				.status(HttpStatus.BAD_REQUEST)
				.build();
		}
		return (HttpResponse)methodHandle.invoke(args.toArray());
	}

	private List<String> resolveArguments(HttpRequest request, List<Class<?>> parameterList) {
		if (request.isMethodGet()) {
			return getRequestParams(request, parameterList);
		}
		// TODO: @RequestBody 처리
		// if (request.isMethodPost()) {
		// 	return getRequestBody(request, parameterList);
		// }
		return Collections.emptyList();
	}

	private List<String> getRequestParams(HttpRequest request, List<Class<?>> parameterList) {
		return parameterList.stream()
			.map(parameter -> parameter.getAnnotation(RequestParam.class))
			.map(requestParam -> request.getUrlParamValue(requestParam.name()))
			.collect(Collectors.toList());
	}

}
