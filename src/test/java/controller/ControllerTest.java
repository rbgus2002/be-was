package controller;

import db.Database;
import http.HttpResponse;
import model.User;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ControllerTest {

    Controller controller = Controller.getInstance();

    @AfterEach
    void tearDown() {
        Database.clear();
    }

    @Test
    @DisplayName("싱글톤으로 생성된다.")
    void createSingleton() {
        //given, when
        Controller c1 = Controller.getInstance();
        Controller c2 = Controller.getInstance();

        //then
        assertThat(c1).isEqualTo(c2);
    }

    @Nested
    class CreateMethod {
        final String userId = "abc";
        final String password = "1q2w3e4r";
        final String name = "kim";
        final String email = "a@a.com";
        final Map<String, String> userParameters = new HashMap<>();

        @BeforeEach
        void setUp() {
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

    private void verifyUser(String userId, String password, String name, String email) {
        User expectedUser = new User(userId, password, name, email);
        User findUser = Database.findUserById(userId);
        assertThat(findUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }
}