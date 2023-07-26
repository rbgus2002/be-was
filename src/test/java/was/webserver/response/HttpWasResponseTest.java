package was.webserver.response;

import java.io.ByteArrayOutputStream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import was.webserver.response.HttpWasResponse;
import was.webserver.utils.HttpHeader;
import was.webserver.utils.HttpMimeType;
import was.webserver.utils.HttpStatus;

class HttpWasResponseTest {

	@Test
	@DisplayName("404 Response를 제대로 생성하는지")
	void create404Header() {
		// given
		String requestLine = "HTTP/1.1 404 NOT FOUND\r\n";
		String contentType = "Content-Type: text/plain;charset=utf-8\r\n";
		String contentLength = "Content-Length: 13\r\n";
		String body = "Not Found";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		httpWasResponse.setHttpStatus(HttpStatus.NOT_FOUND);
		httpWasResponse.setBody(HttpStatus.NOT_FOUND.getName(), HttpMimeType.PLAIN);
		httpWasResponse.doResponse();

		// then
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(outputStream.toString()).as("requestLine 테스트").contains(requestLine);
		softAssertions.assertThat(outputStream.toString()).as("contentType 테스트").contains(contentType);
		softAssertions.assertThat(outputStream.toString()).as("contentLength 테스트").contains(contentLength);
		softAssertions.assertThat(outputStream.toString()).as("body 테스트").contains(body);
	}

	@Test
	@DisplayName("302 Header를 제대로 생성하는지")
	void create302Header() {
		// given
		String locationPath = "http:localhost:8080/index.html";
		String requestLine = "HTTP/1.1 302 FOUND\r\n";
		String location = "Location: " + locationPath + "\r\n";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		httpWasResponse.setHttpStatus(HttpStatus.FOUND);
		httpWasResponse.addHeader(HttpHeader.LOCATION, locationPath);
		httpWasResponse.doResponse();

		// then
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(outputStream.toString()).as("requestLine 테스트").contains(requestLine);
		softAssertions.assertThat(outputStream.toString()).as("location 테스트").contains(location);
	}

	@Test
	@DisplayName("405 Header를 제대로 생성하는지")
	void create405Header() {
		// given
		String requestLine = "HTTP/1.1 405 Method Not Allowed\r\n";
		String contentType = "Content-Type: text/plain;charset=utf-8\r\n";
		String contentLength = "Content-Length: 22\r\n";
		String body = "Method Not Allowed\r\n";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		httpWasResponse.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
		httpWasResponse.setBody(HttpStatus.METHOD_NOT_ALLOWED.getName(), HttpMimeType.PLAIN);
		httpWasResponse.doResponse();

		// then
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(outputStream.toString()).as("requestLine 테스트").contains(requestLine);
		softAssertions.assertThat(outputStream.toString()).as("contentType 테스트").contains(contentType);
		softAssertions.assertThat(outputStream.toString()).as("contentLength 테스트").contains(contentLength);
		softAssertions.assertThat(outputStream.toString()).as("body 테스트").contains(body);
	}
}