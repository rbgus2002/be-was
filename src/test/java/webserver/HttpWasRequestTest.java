package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}