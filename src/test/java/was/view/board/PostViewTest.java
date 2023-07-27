package was.view.board;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import was.db.Database;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;
import was.webserver.utils.HttpStatus;

class PostViewTest {

	private final PostView postView = new PostView();

	@BeforeEach
	void beforeEach() {
		Database.deleteUserAll();
		Database.deleteBoardAll();
	}

	@Test
	@DisplayName("로그인을 하지 않으면 index로 이동해야한다")
	void notLoginUserIsRedirect() throws IOException {
		// given
		String input = "GET /user/list HTTP/1.1\r\n"
			+ "\r\n";
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		postView.post(httpWasRequest, httpWasResponse);
		httpWasResponse.doResponse();

		// then
		Assertions.assertThat(outputStream.toString()).contains(HttpStatus.FOUND.getName());
	}
}
