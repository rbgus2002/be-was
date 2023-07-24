package webserver.http.controller;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import webserver.container.ControllerScanner;
import webserver.http.message.HttpMethod;

@ExtendWith(SoftAssertionsExtension.class)
class ControllerResolverTest {

	@InjectSoftAssertions
	SoftAssertions softly;

	@BeforeEach
	void setUp() {
		ControllerScanner.initialize();
	}

	@Test
	@DisplayName("URL과 HTTP METHOD에 따른 컨트롤러를 호출한다")
	void resolve() {
		ControllerResolver controllerResolver = new ControllerResolver();
		String success = controllerResolver.resolve("/index", HttpMethod.GET);
		String fail = controllerResolver.resolve("/not-found", HttpMethod.GET);

		softly.assertThat(success).isEqualTo("index");
		softly.assertThat(fail).isNull();
	}

}
