package webserver.request;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.request.HttpWasRequest;

class HttpWasRequestTest {

	@Test
	@DisplayName("Resouce Path에 대한 파싱을 제대로 해야한다")
	void checkResourcePath() throws IOException {
		// given
		String request = "GET /index.html HTTP/1.1 \r\nHost: localhost:8080\r\n";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());

		// when
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);

		// then
		assertThat(httpWasRequest.getResourcePath()).isEqualTo("/index.html");
	}

	@Test
	@DisplayName("제대로 Query Param과 Attribute가 변환이 되야한다")
	void convertToBase64() throws IOException {
		// given
		String request = "GET /user/create?userId=haechan&password=password&name=%EC%9C%A0%ED%95%B4%EC%B0%AC&email=a%40naver.com HTTP/1.1 \r\n"
			+ "Host: localhost:8080\r\n"
			+ "Connection: keep-alive\r\n"
			+ "Accept: */*\r\n";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());

		// when
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);

		// then
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(httpWasRequest.getParameter("userId")).isEqualTo("haechan");
		softAssertions.assertThat(httpWasRequest.getParameter("password")).isEqualTo("password");
		softAssertions.assertThat(httpWasRequest.getParameter("name")).isEqualTo("유해찬");
		softAssertions.assertThat(httpWasRequest.getParameter("email")).isEqualTo("a@naver.com");
		softAssertions.assertThat(httpWasRequest.getAttribute("Host")).isEqualTo("localhost:8080");
		softAssertions.assertThat(httpWasRequest.getAttribute("Accept")).isEqualTo("*/*");
	}
}