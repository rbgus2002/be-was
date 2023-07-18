package webserver.response;

import java.io.ByteArrayOutputStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpWasResponseTest {

	@Test
	@DisplayName("404 Response를 제대로 생성하는지")
	void create404Header() {
		// given
		String response = "HTTP/1.1 404 NOT FOUND\r\n"
			+ "Content-Type: text/plain;charset=utf-8\r\n"
			+ "Content-Length: 13\r\n"
			+ "\r\n"
			+ "404 Not Found";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		httpWasResponse.response404();
		httpWasResponse.doResponse();

		// then
		Assertions.assertThat(outputStream.toString()).hasToString(response);
	}

	@Test
	@DisplayName("302 Header를 제대로 생성하는지")
	void create302Header() {
		// given
		String location = "http:localhost:8080/index.html";
		String response = "HTTP/1.1 302 FOUND\r\n"
			+ "Location: " + location + "\r\n";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		httpWasResponse.response302Header(location);
		httpWasResponse.doResponse();

		// then
		Assertions.assertThat(outputStream.toString()).hasToString(response);
	}
}