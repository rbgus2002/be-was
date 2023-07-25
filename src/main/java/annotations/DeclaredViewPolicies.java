package annotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.view.Model;

public class DeclaredViewPolicies {
	private static Object instance;
	private static Map<String, Method> viewPolicies = new HashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(DeclaredViewPolicies.class);

	private DeclaredViewPolicies() {
	}
	public static void initialize() {
		try {
			scanPolicies();
			initInstance();
		} catch (ReflectiveOperationException e) {
			logger.debug("ViewPolicy를 불러올 수 없습니다. message : {}", e.getMessage());
		}
	}

	public static String runPolicyFor(final String regex, final String line, final Model model) throws
		InvocationTargetException,
		IllegalAccessException {
		return (String)viewPolicies.get(regex).invoke(instance, line, model);
	}

	public static List<String> getViewPoliciesRegex() {
		return viewPolicies.keySet().stream().collect(Collectors.toList());
	}

	private static void scanPolicies() {
		final Method[] declaredMethods = webserver.view.ViewPolicy.class.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			if (declaredMethod.isAnnotationPresent(annotations.ViewPolicy.class)) {
				viewPolicies.put(declaredMethod.getAnnotation(annotations.ViewPolicy.class).regex(), declaredMethod);
			}
		}
	}

	private static void initInstance() throws ReflectiveOperationException {
		Constructor<webserver.view.ViewPolicy> viewPolicyConstructor = webserver.view.ViewPolicy.class.getDeclaredConstructor();
		instance = viewPolicyConstructor.newInstance();
	}

}
