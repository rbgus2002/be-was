package webserver.mapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerMapping {

	private final Map<Class<?>, Object> mapping = new HashMap<>();

	private ControllerMapping() {
	}

	public static ControllerMapping getInstance() {
		return ControllerMapping.LazyHolder.instance;
	}

	public void add(Class<?> clazz, Object obj) {
		mapping.put(clazz, obj);
	}

	public Object getControllerByMethod(Method method) {
		return mapping.get(method.getDeclaringClass());
	}

	private static class LazyHolder {
		private static final ControllerMapping instance = new ControllerMapping();
	}
}
