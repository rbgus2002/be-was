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
import was.model.User;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;
import was.webserver.session.HttpSession;
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

	@Test
	@DisplayName("포스트가 없는 게시물에 접근하면 404가 반환된다")
	void noPostJoinThenReturn404() throws IOException {
		// given
		final User user = new User("chan", "1234", "chan", "chan@naver.com");
		final HttpSession httpSession = HttpSession.getInstance();
		final String sessionID = httpSession.createSession(user.getUserId());

		String input = "GET /post?index=1 HTTP/1.1\r\n"
			+ "Cookie: SID="+sessionID+"\r\n"
			+ "\r\n";
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		postView.post(httpWasRequest, httpWasResponse);
		httpWasResponse.doResponse();

		// then
		Assertions.assertThat(outputStream.toString()).contains(HttpStatus.NOT_FOUND.getName());
	}

	@Test
	@DisplayName("post에 index를 명시하지 않으면 404를 반환한다")
	void postMustHavaIndex() throws IOException {
		// given
		final User user = new User("chan", "1234", "chan", "chan@naver.com");
		final HttpSession httpSession = HttpSession.getInstance();
		final String sessionID = httpSession.createSession(user.getUserId());

		String input = "GET /post HTTP/1.1\r\n"
			+ "Cookie: SID="+sessionID+"\r\n"
			+ "\r\n";
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		postView.post(httpWasRequest, httpWasResponse);
		httpWasResponse.doResponse();

		// then
		Assertions.assertThat(outputStream.toString()).contains(HttpStatus.NOT_FOUND.getName());
	}
}
