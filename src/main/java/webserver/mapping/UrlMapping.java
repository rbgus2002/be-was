package webserver.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import webserver.http.message.HttpMethod;

public class UrlMapping {

	private final Map<UrlHttpMethodPair, Method> mapping = new HashMap<>();

	private UrlMapping() {
	}

	public static UrlMapping getInstance() {
		return LazyHolder.instance;
	}

	public void add(UrlHttpMethodPair pair, Method method) {
		mapping.put(pair, method);
	}

	public Method find(String path, HttpMethod httpMethod) {
		return mapping.get(UrlHttpMethodPair.of(path, httpMethod));
	}

	private static class LazyHolder {
		private static final UrlMapping instance = new UrlMapping();
	}
}
