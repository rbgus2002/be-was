package webserver.mapping;

import java.util.HashMap;
import java.util.Map;

public class ControllerMapping {

	private final Map<Class<?>, Object> mapping = new HashMap<>();

	private ControllerMapping() {
	}

	public static ControllerMapping getInstance() {
		return LazyHolder.instance;
	}

	public void add(Class<?> clazz, Object controller) {
		mapping.put(clazz, controller);
	}

	public Object find(Class<?> clazz) {
		return mapping.get(clazz);
	}

	private static class LazyHolder {
		private static final ControllerMapping instance = new ControllerMapping();
	}
}
