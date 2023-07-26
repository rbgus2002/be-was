package view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.Database;
import model.User;
import webserver.request.HttpWasRequest;
import webserver.response.HttpWasResponse;
import webserver.session.HttpSession;

class IndexViewTest {

	private final IndexView indexView = new IndexView();

	@BeforeEach
	void beforeEach() {
		Database.deleteAll();
	}

	@Test
	@DisplayName("로그인을 했으면 index에 회원 이름이 나와야한다")
	void loginUserNameIsExist() throws IOException {
		// given
		final User user = new User("chan", "1234", "haechan", "chan@naver.com");
		final HttpSession httpSession = HttpSession.getInstance();
		final String session = httpSession.createSession(user.getUserId());

		Database.addUser(user);
		String input = "GET /user/index HTTP/1.1\r\n"
			+ "Cookie: SID=" + session + "\r\n"
			+ "\r\n";
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);



		// when
		indexView.index(httpWasRequest, httpWasResponse);
		httpWasResponse.doResponse();

		// then
		Assertions.assertThat(outputStream.toString()).contains(user.getName());
	}
}