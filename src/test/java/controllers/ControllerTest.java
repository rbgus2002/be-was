package controllers;

import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import db.Database;
import http.Parameter;
import model.User;

public class ControllerTest {

	private Controller controller;

	@BeforeEach
	void setUp() {
		controller = new Controller();
	}

	@Nested
	@DisplayName("/create")
	class createController {

		@BeforeEach
		void clearDatabase() {
			Database.dropAll();
		}

		@Test
		@DisplayName("GET 요청의 파라미터를 통해 데이터베이스에 User를 추가할 수 있어야 한다")
		void registerUser() {
			final Parameter parameter = newParameter("testID", "testPassword", "testName", "test@email.com");

			controller.createUser(parameter);

			verifyRegister(parameter);
		}

		@Test
		@DisplayName("이미 데이터베이스에 존재하는 userId로 가입하려고 하면 예외가 발생하고 저장되지 않아야 한다")
		void duplicateRegistration() {
			final String userId = "testID";
			final String password = "testPassword";
			final String name = "testName";
			final String email = "test@email.com";
			final User existingUser = setUpUser(userId, password, name, email);
			Parameter parameter = newParameter("testID", "testPassword2", "testName2", "test2@email.com");

			SoftAssertions softAssertions = new SoftAssertions();
			softAssertions.assertThatThrownBy(() -> {
				controller.createUser(parameter);
			}).isInstanceOf(Exception.class).hasMessage(Database.USERID_ALREADY_EXISTS_MESSAGE);

			softAssertions.assertThat(Database.findUserById(existingUser.getUserId()).getPassword()).isEqualTo(existingUser.getPassword());
			softAssertions.assertThat(Database.findUserById(existingUser.getUserId()).getName()).isEqualTo(existingUser.getName());
			softAssertions.assertThat(Database.findUserById(existingUser.getUserId()).getEmail()).isEqualTo(existingUser.getEmail());
			softAssertions.assertThat(Database.findAll().size()).isEqualTo(1);

			softAssertions.assertAll();
		}

		private User setUpUser(final String userId, final String password, final String name, final String email) {
			final User existingUser = new User(userId, password, name, email);
			Database.addUser(existingUser);
			return existingUser;
		}

		private void verifyRegister(final Parameter parameter) {
			SoftAssertions softAssertions = new SoftAssertions();
			final User databaseUser = Database.findUserById(parameter.getParameter("userId"));

			softAssertions.assertThat(databaseUser).isNotNull();
			softAssertions.assertThat(databaseUser.getUserId()).isEqualTo(parameter.getParameter("userId"));
			softAssertions.assertThat(databaseUser.getPassword()).isEqualTo(parameter.getParameter("password"));
			softAssertions.assertThat(databaseUser.getName()).isEqualTo(parameter.getParameter("name"));
			softAssertions.assertThat(databaseUser.getEmail()).isEqualTo(parameter.getParameter("email"));

			softAssertions.assertAll();
		}

		private Parameter newParameter(final String userId, final String password, final String name, final String email) {
			final Parameter parameter = new Parameter();
			Map<String, String> map = Map.of("userId", userId, "password", password, "name", name, "email", email);
			map.keySet().stream().forEach(key -> parameter.put(key, map.get(key)));
			return parameter;
		}

		@Test
		@DisplayName("회원가입 성공 이후 메인 페이지로 리다이렉트 되어야 한다")
		void sendRedirect() {
			
		}

	}
}
