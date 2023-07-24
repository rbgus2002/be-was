package webserver.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import webapp.controller.HomeController;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.http.message.HttpMethod;

@ExtendWith(SoftAssertionsExtension.class)
class ControllerScannerTest {

	String path = "/index";
	HttpMethod httpMethod = HttpMethod.GET;
	String viewName = "index";
	Class<?> clazz = HomeController.class;
	@InjectSoftAssertions
	private SoftAssertions softly;

	@BeforeAll
	static void setUp() {
		ControllerScanner.initialize();
	}

	@Test
	@DisplayName("/webapp 경로에서 모든 @RequestMapping 메소드를 스캔해 UrlMapping에 저장한다")
	void urlMapping() throws InvocationTargetException, IllegalAccessException {
		UrlMapping urlMapping = UrlMapping.getInstance();
		Method method = urlMapping.find(path, httpMethod);
		softly.assertThat(method.getAnnotation(RequestMapping.class)).isNotNull();
		softly.assertThat(method.invoke(new HomeController())).isEqualTo(viewName);
	}

	@Test
	@DisplayName("/webapp 경로에서 모든 @Controller 클래스를 스캔해 ControllerMapping에 저장한다")
	void controllerMapping() throws InvocationTargetException, IllegalAccessException {
		UrlMapping urlMapping = UrlMapping.getInstance();
		Method method = urlMapping.find(path, httpMethod);
		ControllerMapping controllerMapping = ControllerMapping.getInstance();

		Object controller = controllerMapping.find(clazz);
		softly.assertThat(controller.getClass().getAnnotation(Controller.class)).isNotNull();
		softly.assertThat(method.invoke(controller)).isEqualTo(viewName);
	}
}
