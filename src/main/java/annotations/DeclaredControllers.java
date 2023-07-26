package annotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controllers.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.statusline.HttpMethod;
import webserver.view.ModelView;

public class DeclaredControllers {

	private static Object instance;
	private static Map<HttpMethod, Map<String, Method>> methodMaps = new HashMap<>();
	private static Map<String, Method> getMethods = new HashMap<>();
	private static Map<String, Method> postMethods = new HashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(DeclaredControllers.class);

	private DeclaredControllers() {
	}

	public static void initialize() {
		try {
			scanMethods();
			initInstance();
		} catch (ReflectiveOperationException e) {
			logger.debug("Controller를 불러올 수 없습니다. message : {}", e.getMessage());
		}
	}

	public static ModelView runController(HttpMethod type, String path, HttpRequest httpRequest, HttpResponse httpResponse,
		ModelView modelView) throws InvocationTargetException, IllegalAccessException {
		return (ModelView)methodMaps.get(type).get(path).invoke(instance, httpRequest, httpResponse, modelView);
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
		Constructor<Controller> controllerConstructor = Controller.class.getDeclaredConstructor();
		instance = controllerConstructor.newInstance();
	}

	public static boolean exists(HttpMethod methodType, String path) {
		return methodMaps.get(methodType).containsKey(path);
	}
}
