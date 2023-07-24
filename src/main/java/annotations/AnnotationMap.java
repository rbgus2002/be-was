package annotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controllers.Controller;
import http.statusline.HttpMethod;
import http.HttpParameter;

public class AnnotationMap {

	private static Object instance;
	private static Map<HttpMethod, Map<String, Method>> methodMaps = new HashMap<>();
	private static Map<String, Method> getMethods = new HashMap<>();
	private static Map<String, Method> postMethods = new HashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(AnnotationMap.class);

	private AnnotationMap() {
	}

	public static String run(HttpMethod type, String path, HttpParameter httpParameter) throws InvocationTargetException, IllegalAccessException {
		return (String)methodMaps.get(type).get(path).invoke(instance, httpParameter);
	}

	public static void initialize() {
		try {
			scanMethods();
			initInstance();
		} catch (ReflectiveOperationException e) {
			logger.debug("Annotation을 불러올 수 없습니다. message : {}", e.getMessage());
		}
	}

	private static void scanMethods() throws ReflectiveOperationException {
		final Method[] declaredMethods = Controller.class.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			if (declaredMethod.isAnnotationPresent(GetMapping.class)) {
				getMethods.put(declaredMethod.getAnnotation(GetMapping.class).path(), declaredMethod);
			}
			if (declaredMethod.isAnnotationPresent(PostMapping.class)) {
				postMethods.put(declaredMethod.getAnnotation(PostMapping.class).path(), declaredMethod);
			}
		}
		methodMaps.put(HttpMethod.GET, getMethods);
		methodMaps.put(HttpMethod.POST, postMethods);
	}

	private static void initInstance() throws ReflectiveOperationException {
		Constructor<Controller> constructor = Controller.class.getDeclaredConstructor();
		instance = constructor.newInstance();
	}

	public static boolean exists(HttpMethod methodType, String path) {
		return methodMaps.get(methodType).containsKey(path);
	}
}
