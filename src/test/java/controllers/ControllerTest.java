package controllers;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import db.UserDatabase;
import webserver.http.HttpParameter;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import model.User;
import webserver.view.ModelView;

class ControllerTest {

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
			UserDatabase.dropAll();
		}

		@Test
		@DisplayName("GET 요청의 파라미터를 통해 데이터베이스에 User를 추가할 수 있어야 한다")
		void registerUser() {
			final HttpParameter httpParameter = newParameter("testID", "testPassword", "testName", "test@email.com");

			controller.createUser(new HttpRequest(httpParameter), new HttpResponse(),  ModelView.from(null));

			verifyRegister(httpParameter);
		}

		@Test
		@DisplayName("이미 데이터베이스에 존재하는 userId로 가입하려고 하면 예외가 발생하고 저장되지 않아야 한다")
		void duplicateRegistration() {
			final String userId = "testID";
			final String password = "testPassword";
			final String name = "testName";
			final String email = "test@email.com";
			final User existingUser = setUpUser(userId, password, name, email);
			HttpParameter httpParameter = newParameter("testID", "testPassword2", "testName2", "test2@email.com");

			SoftAssertions softAssertions = new SoftAssertions();
			softAssertions.assertThatThrownBy(() -> {
				controller.createUser(new HttpRequest(httpParameter), new HttpResponse(), ModelView.from(null));
			}).isInstanceOf(Exception.class).hasMessage(UserDatabase.USERID_ALREADY_EXISTS_MESSAGE);

			softAssertions.assertThat(UserDatabase.findUserById(existingUser.getUserId()).getPassword())
				.isEqualTo(existingUser.getPassword());
			softAssertions.assertThat(UserDatabase.findUserById(existingUser.getUserId()).getName())
				.isEqualTo(existingUser.getName());
			softAssertions.assertThat(UserDatabase.findUserById(existingUser.getUserId()).getEmail())
				.isEqualTo(existingUser.getEmail());
			softAssertions.assertThat(UserDatabase.findAll().size()).isEqualTo(1);

			softAssertions.assertAll();
		}

		private User setUpUser(final String userId, final String password, final String name, final String email) {
			final User existingUser = new User(userId, password, name, email);
			UserDatabase.addUser(existingUser);
			return existingUser;
		}

		private void verifyRegister(final HttpParameter httpParameter) {
			SoftAssertions softAssertions = new SoftAssertions();
			final User databaseUser = UserDatabase.findUserById(httpParameter.getParameter("userId"));

			softAssertions.assertThat(databaseUser).isNotNull();
			softAssertions.assertThat(databaseUser.getUserId()).isEqualTo(httpParameter.getParameter("userId"));
			softAssertions.assertThat(databaseUser.getPassword()).isEqualTo(httpParameter.getParameter("password"));
			softAssertions.assertThat(databaseUser.getName()).isEqualTo(httpParameter.getParameter("name"));
			softAssertions.assertThat(databaseUser.getEmail()).isEqualTo(httpParameter.getParameter("email"));

			softAssertions.assertAll();
		}

		private HttpParameter newParameter(final String userId, final String password, final String name,
			final String email) {
			final HttpParameter httpParameter = new HttpParameter();
			Map<String, String> map = Map.of("userId", userId, "password", password, "name", name, "email", email);
			map.keySet().stream().forEach(key -> httpParameter.put(key, map.get(key)));
			return httpParameter;
		}

		@Test
		@DisplayName("회원가입 성공 이후 메인 페이지로 리다이렉트 되어야 한다")
		void sendRedirect() {
			final HttpParameter httpParameter = newParameter("testID", "testPassword", "testName", "test@email.com");
			ModelView modelView = controller.createUser(new HttpRequest(httpParameter), new HttpResponse(), ModelView.from(null));
			assertThat(modelView.getPath()).isEqualTo("redirect:/");
		}

	}
}
