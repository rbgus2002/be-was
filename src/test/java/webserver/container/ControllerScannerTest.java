package webserver.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import webapp.controller.HomeController;
import webserver.annotation.RequestMapping;
import webserver.http.message.HttpMethod;

@ExtendWith(SoftAssertionsExtension.class)
class ControllerScannerTest {

	@InjectSoftAssertions
	private SoftAssertions softly;

	@Test
	@DisplayName("/webapp 경로에서 @Controller 클래스의 @RequestMapping 메소드를 스캔해 UrlMapping에 저장한다")
	void findControllers() throws InvocationTargetException, IllegalAccessException {
		// given
		String path = "/index";
		HttpMethod httpMethod = HttpMethod.GET;
		String viewName = "index";
		// when
		ControllerScanner.initialize();
		UrlMapping urlMapping = UrlMapping.getInstance();
		// then
		Method method = urlMapping.findRequestMapping(path, httpMethod);
		softly.assertThat(method.getAnnotation(RequestMapping.class)).isNotNull();
		softly.assertThat(method.invoke(new HomeController())).isEqualTo(viewName);
	}
}
