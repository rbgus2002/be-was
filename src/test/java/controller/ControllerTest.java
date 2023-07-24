package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.Database;
import model.User;
import webserver.request.HttpWasRequest;
import webserver.response.HttpWasResponse;

class ControllerTest {

	@BeforeEach
	void beforeEach() {
		Database.deleteAll();
	}

	@Test
	@DisplayName("제대로 회원가입이 되야한다")
	void saveUser() throws IOException {
		// given
		Controller controller = new Controller();

		String input = "POST /user/create HTTP/1.1\r\n"
			+ "Content-Type: application/x-www-form-urlencoded\r\n"
			+ "Content-Length: 53\r\n"
			+ "\r\n"
			+ "userId=chan&password=123&name=chan&email=a%40naver.com";
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());
		final HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);
		final HttpWasResponse httpWasResponse = new HttpWasResponse(new ByteArrayOutputStream());

		//when
		controller.saveUser(httpWasRequest, httpWasResponse);

		//then
		final User findUser = Database.findUserById("chan");
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(findUser.getUserId()).isEqualTo("chan");
		softAssertions.assertThat(findUser.getEmail()).isEqualTo("a@naver.com");
		softAssertions.assertThat(findUser.getName()).isEqualTo("chan");
		softAssertions.assertThat(findUser.getPassword()).isEqualTo("123");
	}

}