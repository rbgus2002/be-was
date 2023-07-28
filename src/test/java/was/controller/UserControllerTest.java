package was.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import was.db.Database;
import was.model.User;
import was.webserver.request.HttpWasRequest;
import was.webserver.response.HttpWasResponse;

class UserControllerTest {

	private UserController userController = new UserController();
	@BeforeEach
	void beforeEach() {
		Database.deleteUserAll();
		Database.deleteBoardAll();
	}

	@Test
	@DisplayName("제대로 회원가입이 되야한다")
	void saveUser() throws IOException {
		// given
		String input = "POST /user/create HTTP/1.1\r\n"
			+ "Content-Type: application/x-www-form-urlencoded\r\n"
			+ "Content-Length: 53\r\n"
			+ "\r\n"
			+ "userId=chan&password=123&name=chan&email=a%40naver.com";
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final HttpWasResponse httpWasResponse = new HttpWasResponse(new ByteArrayOutputStream());

		//when
		userController.saveUser(httpWasRequest, httpWasResponse);

		//then
		final User findUser = Database.findUserById("chan");
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(findUser.getUserId()).isEqualTo("chan");
		softAssertions.assertThat(findUser.getEmail()).isEqualTo("a@naver.com");
		softAssertions.assertThat(findUser.getName()).isEqualTo("chan");
		softAssertions.assertThat(findUser.getPassword()).isEqualTo("123");
	}

	@Test
	@DisplayName("제대로 로그인이 된다면 쿠키에 세션 ID가 반환이 되어야 한다")
	void loginReturnCookie() throws IOException {
		// given
		final User user = new User("chan", "1234", "chan", "c@naver.com");
		Database.addUser(user);

		String input = "POST /user/login HTTP/1.1\r\n"
			+ "Content-Type: application/x-www-form-urlencoded\r\n"
			+ "Content-Length: 25\r\n"
			+ "\r\n"
			+ "userId=chan&password=1234";

		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		userController.loginUser(httpWasRequest, httpWasResponse);
		httpWasResponse.doResponse();

		// then
		assertThat(outputStream.toString()).contains("SID");
	}

	@Test
	@DisplayName("로그아웃을 하면 쿠키가 만료되어야한다")
	void expiredCookieDoLogout() throws IOException {
		// given
		final User user = new User("chan", "1234", "chan", "c@naver.com");
		Database.addUser(user);

		String input = "GET /logout HTTP/1.1\r\n"
			+ "Cookie: SID=12345\r\n"
			+ "\r\n";

		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final HttpWasResponse httpWasResponse = new HttpWasResponse(outputStream);

		// when
		userController.logout(httpWasRequest, httpWasResponse);
		httpWasResponse.doResponse();

		// then
		assertThat(outputStream.toString()).contains("Max-Age=0");
	}

}