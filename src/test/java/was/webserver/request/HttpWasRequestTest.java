package was.webserver.request;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import was.webserver.request.HttpWasRequest;

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
		softAssertions.assertThat(httpWasRequest.getParameter("userId")).as("userId 테스트").isEqualTo("haechan");
		softAssertions.assertThat(httpWasRequest.getParameter("password")).as("password 테스트").isEqualTo("password");
		softAssertions.assertThat(httpWasRequest.getParameter("name")).as("name 테스트").isEqualTo("유해찬");
		softAssertions.assertThat(httpWasRequest.getParameter("email")).as("email 테스트").isEqualTo("a@naver.com");
		softAssertions.assertThat(httpWasRequest.getAttribute("Host")).as("host 테스트").isEqualTo("localhost:8080");
		softAssertions.assertThat(httpWasRequest.getAttribute("Accept")).as("Accept 테스트").isEqualTo("*/*");
	}

	@Test
	@DisplayName("x-www-form-urlencoded는 body의 값을 제대로 파싱할 수 있어야한다")
	void parseXWwwFormInBody() throws IOException {
		// given
		String request = "POST /user/create HTTP/1.1 \r\n"
			+ "Host: localhost:8080\r\n"
			+ "Connection: keep-alive\r\n"
			+ "Accept: */*\r\n"
			+ "\r\n"
			+ "userId=haechan&password=password&name=%EC%9C%A0%ED%95%B4%EC%B0%AC&email=a%40naver.com ";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());

		// when
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);

		// then
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(httpWasRequest.getParameter("userId")).as("userId 테스트").isEqualTo("haechan");
		softAssertions.assertThat(httpWasRequest.getParameter("password")).as("password 테스트").isEqualTo("password");
		softAssertions.assertThat(httpWasRequest.getParameter("name")).as("name 테스트").isEqualTo("유해찬");
		softAssertions.assertThat(httpWasRequest.getParameter("email")).as("email 테스트").isEqualTo("a@naver.com");
		softAssertions.assertThat(httpWasRequest.getAttribute("Host")).as("host 테스트").isEqualTo("localhost:8080");
		softAssertions.assertThat(httpWasRequest.getAttribute("Accept")).as("Accept 테스트").isEqualTo("*/*");
	}

	@Test
	@DisplayName("name과 value만 있어도 SID를 제대로 가져와야한다")
	void getSessionIdOnlyNameAndValue() throws IOException {
		// given
		String request = "POST /user/login HTTP/1.1 \r\n"
			+ "Host: localhost:8080\r\n"
			+ "Cookie: IDEA=124533; SID=12345;";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());

		// when
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);

		// then
		assertThat(httpWasRequest.getSessionId()).isEqualTo("12345");
	}

	@Test
	@DisplayName("Cookie가 2개 이상이여도 SID를 제대로 가져와야한다")
	void getSessionIdOverThanTwo() throws IOException {
		// given
		String request = "POST /user/login HTTP/1.1 \r\n"
			+ "Host: localhost:8080\r\n"
			+ "Cookie: IDEA=124533; Max-Age=3600; HttpOnly;\r\n"
			+ "Cookie: SID=12345; Max-Age=3600";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());

		// when
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);

		// then
		assertThat(httpWasRequest.getSessionId()).isEqualTo("12345");
	}
}
