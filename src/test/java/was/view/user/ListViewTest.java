package was.view.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import was.db.Database;
import was.model.User;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;
import was.webserver.session.HttpSession;
import was.webserver.utils.HttpStatus;

class ListViewTest {

	private final ListView listView = new ListView();

	@BeforeEach
	void beforeEach() {
		Database.deleteUserAll();
		Database.deleteBoardAll();
	}

	@Test
	@DisplayName("로그인을 했으면 list에 회원 정보가 나와야한다")
	void loginUserNameIsExist() throws IOException {
		// given
		final User user = new User("chan", "1234", "haechan", "chan@naver.com");
		final HttpSession httpSession = HttpSession.getInstance();
		final String session = httpSession.createSession(user.getUserId());

		Database.addUser(user);
		String input = "GET /user/list HTTP/1.1\r\n"
			+ "Cookie: SID=" + session + "\r\n"
			+ "\r\n";
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);



		// when
		listView.list(httpWasRequest, httpWasResponse);
		httpWasResponse.doResponse();

		// then
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(outputStream.toString()).as("userId가 제대로 나오는지").contains(user.getUserId());
		softAssertions.assertThat(outputStream.toString()).as("username이 제대로 나오는지").contains(user.getName());
		softAssertions.assertThat(outputStream.toString()).as("userEmail이 제대로 나오는지").contains(user.getEmail());
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
		listView.list(httpWasRequest, httpWasResponse);
		httpWasResponse.doResponse();

		// then
		Assertions.assertThat(outputStream.toString()).contains(HttpStatus.FOUND.getName());
	}
}