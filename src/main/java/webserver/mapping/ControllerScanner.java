package webserver.mapping;

import java.io.File;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;

public class ControllerScanner {

	private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

	private static final String PROJECT_PATH = "src/main/java/";
	private static final String APP_PATH = "webapp";
	private static final String EXTENSION = ".java";

	private static ControllerScanner instance = null;
	private final ControllerMapping controllerMapping = ControllerMapping.getInstance();
	private final UrlMapping urlMapping = UrlMapping.getInstance();

	private ControllerScanner() {
	}

	public static void initialize() {
		if (instance != null) {
			return;
		}
		instance = new ControllerScanner();
		try {
			instance.scanControllers();
		} catch (ReflectiveOperationException e) {
			logger.error(e.getMessage());
		}
	}

	private void scanControllers() throws ReflectiveOperationException {
		File appDirectory = new File(PROJECT_PATH + APP_PATH);
		if (!appDirectory.exists()) {
			throw new ClassNotFoundException();
		}
		findControllerRecur(appDirectory);
	}

	private void findControllerRecur(File directory) throws ReflectiveOperationException {
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				addController(file);
				return;
			}
			findControllerRecur(file);
		}
	}

	private void addController(File controller) throws ReflectiveOperationException {
		String classFilePath = controller.getPath();
		String className = classFilePath.substring(PROJECT_PATH.length(), classFilePath.indexOf(EXTENSION))
			.replace("/", ".");
		Class<?> clazz = Class.forName(className);
		if (clazz.isAnnotationPresent(Controller.class)) {
			controllerMapping.add(clazz, clazz.getConstructor().newInstance());
			scanRequestMapping(clazz);
		}
	}

	private void scanRequestMapping(Class<?> controller) {
		for (Method method : controller.getMethods()) {
			RequestMapping annotation = method.getAnnotation(RequestMapping.class);
			if (annotation == null) {
				continue;
			}
			// TODO: 메소드 반환 타입이 String인지 확인
			UrlHttpMethodPair urlHttpMethodPair = UrlHttpMethodPair.of(annotation.path(), annotation.method());
			urlMapping.add(urlHttpMethodPair, method);
		}
	}
}
