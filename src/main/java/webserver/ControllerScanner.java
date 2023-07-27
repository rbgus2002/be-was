package webserver;

import java.io.File;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.mapping.ControllerMapping;
import webserver.mapping.UrlMapping;

public class ControllerScanner {

	private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

	private static final String PROJECT_PATH = "src/main/java/";
	private static final String CONTROLLER_PATH = "webapp/controller";
	private static final String EXTENSION = ".java";

	private final UrlMapping urlMapping = UrlMapping.getInstance();
	private final ControllerMapping controllerMapping = ControllerMapping.getInstance();

	private ControllerScanner() {
	}

	public static void initialize() {
		try {
			ControllerScanner controllerScanner = new ControllerScanner();
			controllerScanner.scan();
		} catch (ReflectiveOperationException e) {
			logger.error(e.getMessage());
		}
	}

	private void scan() throws ReflectiveOperationException {
		File appDirectory = new File(PROJECT_PATH + CONTROLLER_PATH);
		if (!appDirectory.isDirectory()) {
			throw new ReflectiveOperationException();
		}
		scanControllers(appDirectory);
	}

	private void scanControllers(File directory) throws ReflectiveOperationException {
		File[] files = directory.listFiles();
		for (File file : files) {
			if (!file.isFile()) {
				continue;
			}
			Class<?> clazz = convertFileToClass(file);
			addControllerMapping(clazz);
			if (clazz.isAnnotationPresent(Controller.class)) {
				scanRequestMappings(clazz);
			}
		}
	}

	private Class<?> convertFileToClass(File file) throws ReflectiveOperationException {
		String path = file.getPath();
		String className = path.substring(PROJECT_PATH.length(), path.lastIndexOf(EXTENSION))
			.replace("/", ".");
		logger.debug(className);
		return Class.forName(className);
	}

	private void addControllerMapping(Class<?> controllerClass) throws ReflectiveOperationException {
		Object controller = controllerClass.getConstructor().newInstance();
		controllerMapping.add(controllerClass, controller);
	}

	private void scanRequestMappings(Class<?> controllerClass) {
		for (Method method : controllerClass.getMethods()) {
			RequestMapping annotation = method.getAnnotation(RequestMapping.class);
			if (annotation == null) {
				continue;
			}
			urlMapping.add(annotation.method(), annotation.path(), method);
		}
	}

}
