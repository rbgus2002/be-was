package was.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import was.controller.annotation.Controller;

public class FrontController {

	private static final Map<String, Object> instances = new HashMap<>();

	public FrontController() throws ReflectiveOperationException {
		initializeInstances();
	}

	private void initializeInstances() throws ReflectiveOperationException {
		final Reflections reflections = new Reflections("was");
		final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
		for (Class<?> aClass : classes) {
			final Constructor<?> constructor = aClass.getDeclaredConstructor();
			instances.put(aClass.getName(), constructor.newInstance());
		}
	}

	public Object getInstance(String className) {
		return instances.get(className);
	}

	public Method[] getDeclaredMethods() {
		return instances.values()
			.stream()
			.flatMap(clazz -> Arrays.stream(clazz.getClass().getDeclaredMethods()))
			.toArray(Method[]::new);
	}
}
