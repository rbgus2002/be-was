package was.webserver.utils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

public final class ClassManager {

	private ClassManager() {
	}

	public static Map<String, Object> getClassInPackage(Class annotation) throws ReflectiveOperationException {
		Map<String, Object> instances = new HashMap<>();
		final Reflections reflections = new Reflections("was");
		final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(annotation);
		for (Class<?> aClass : classes) {
			final Constructor<?> constructor = aClass.getDeclaredConstructor();
			instances.put(aClass.getName(), constructor.newInstance());
		}
		return instances;
	}
}
