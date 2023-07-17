package service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.Database;
import model.User;
import webserver.HttpWasRequest;

class UserServiceTest {

	private final UserService userService = new UserService();

	@BeforeEach
	void beforeEach() {
		Database.deleteAll();
	}

	@Test
	@DisplayName("User가 제대로 저장되는지 확인하는 로직 작성")
	void saveUser() throws IOException {
		// given
		String request = "GET /user/create?userId=user&password=password&name=chan&email=a%40naver.com HTTP/1.1 \r\n"
			+ "Host: localhost:8080\r\n";
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
		HttpWasRequest httpWasRequest = new HttpWasRequest(inputStream);

		// when
		userService.saveUser(httpWasRequest);

		// then
		final User user = new User("user", "password", "chan", "a@naver.com");
		Assertions.assertThat(Database.findUserById("user")).usingRecursiveComparison().isEqualTo(user);
	}
}