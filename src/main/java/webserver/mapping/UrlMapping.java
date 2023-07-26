package webserver.mapping;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import webserver.http.message.HttpMethod;

public class UrlMapping {

	private final Map<HttpMethod, Map<String, Method>> mapping = new EnumMap<>(HttpMethod.class);

	private UrlMapping() {
		for (HttpMethod httpMethod : HttpMethod.values()) {
			mapping.put(httpMethod, new HashMap<>());
		}
	}

	public static UrlMapping getInstance() {
		return LazyHolder.instance;
	}

	public void add(HttpMethod httpMethod, String path, Method method) {
		mapping.get(httpMethod).put(path, method);
	}

	public Method getMethod(HttpMethod httpMethod, String path) {
		return mapping.get(httpMethod).get(path);
	}

	private static class LazyHolder {
		private static final UrlMapping instance = new UrlMapping();
	}
}
