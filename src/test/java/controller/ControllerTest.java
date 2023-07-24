package controller;

import db.Database;
import db.Session;
import http.HttpResponse;
import model.User;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ControllerTest {

    Controller controller = new Controller();

    final String userId = "abc";
    final String password = "1q2w3e4r";
    final String name = "kim";
    final String email = "a@a.com";

    @AfterEach
    void tearDown() {
        Database.clear();
    }

    @Nested
    class CreateMethod {

        final Map<String, String> userParameters = new HashMap<>();

        @BeforeEach
        void setUp() {
            userParameters.clear();
            userParameters.put("userId", userId);
            userParameters.put("password", password);
            userParameters.put("name", name);
            userParameters.put("email", email);
        }

        @Test
        @DisplayName("create()를 실행하면 DB에 유저를 저장하고 HttpResponse를 반환한다.")
        void createHttpResponse() {
            //given
            //when
            HttpResponse httpResponse = controller.creatUser(userParameters);

            //then
            verifyUser(userId, password, name, email);
            assertThat(httpResponse).usingRecursiveComparison().isEqualTo(HttpResponse.redirect("/index.html"));
        }

        @Test
        @DisplayName("이미 존재하는 userId면 예외가 발생한다.")
        void createDuplicatedUserId() {
            //given
            String existPassword = "123456";
            String existName = "han";
            String existEmail = "b@b.com";
            Database.addUser(new User(userId, existPassword, existName, existEmail));

            //when, then
            assertThrows(IllegalArgumentException.class, () -> controller.creatUser(userParameters));
            verifyUser(userId, existPassword, existName, existEmail);
        }
    }

    @Nested
    class Login {

        @Test
        @DisplayName("아이디와 패스워드를 확인해 로그인한다.")
        void loginSuccess() {
            //given
            User user = new User(userId, password, name, email);
            Database.addUser(user);
            Map<String, String> loginParameters = new HashMap<>();
            loginParameters.put("userId", userId);
            loginParameters.put("password", password);

            //when
            HttpResponse httpResponse = controller.login(loginParameters, new Session());

            //then
            assertThat(httpResponse).usingRecursiveComparison().isEqualTo(HttpResponse.redirect("/index.html"));
        }

        @Test
        @DisplayName("userId로 사용자를 찾을 수 없으면 login failed 페이지로 리다이렉트한다.")
        void userNotExist() {
            //given
            String noId = "kjdfksj";
            Map<String, String> loginParameters = new HashMap<>();
            loginParameters.put("userId", noId);
            loginParameters.put("password", password);

            //when
            HttpResponse response = controller.login(loginParameters, new Session());

            //then
            HttpResponse expected = HttpResponse.redirect("/user/login_failed.html");
            assertThat(response).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        @DisplayName("userId 찾은 사용자의 비밀번호가 다르면 login failed 페이지로 리다이렉트한다.")
        void passwordNotMatch() {
            //given
            String invalidPassword = "kjdfksj";
            Map<String, String> loginParameters = new HashMap<>();
            loginParameters.put("userId", userId);
            loginParameters.put("password", invalidPassword);

            //when
            HttpResponse response = controller.login(loginParameters, new Session());

            //then
            HttpResponse expected = HttpResponse.redirect("/user/login_failed.html");
            assertThat(response).usingRecursiveComparison().isEqualTo(expected);
        }
    }

    private void verifyUser(String userId, String password, String name, String email) {
        User expectedUser = new User(userId, password, name, email);
        Optional<User> findUser = Database.findUserById(userId);
        assertThat(findUser).usingRecursiveComparison().isEqualTo(Optional.of(expectedUser));
    }
}