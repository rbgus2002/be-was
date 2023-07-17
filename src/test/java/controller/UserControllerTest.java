package controller;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

import static org.assertj.core.api.Assertions.*;


@DisplayName("User 도메인 컨트롤러 테스트")
class UserControllerTest {
    UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Nested
    @DisplayName("signUp method")
    class SignUp {
        @Nested
        @DisplayName("요청 파라미터들이 null이 아닌 경우")
        class RequestParametersNotNull {
            @Test
            @DisplayName("요청으로부터 파라미터를 받아 유저 객체를 생성하고 데이터베이스에 저장한다")
            void createUserByRequestParametersAndSaveToDatabase() {
                //given
                HttpRequest httpRequest = HttpRequest.builder()
                        .addParameter("userId", "userId")
                        .addParameter("password", "password")
                        .addParameter("name", "name")
                        .addParameter("email", "email")
                        .build();

                HttpResponse httpResponse = HttpResponse.getInstance();

                //when
                userController.signUp(httpRequest, httpResponse);

                //then
                User user = Database.findUserById("userId");
                assertThat(user)
                        .isEqualTo(new User("userId", "password", "name", "email"));
            }
        }

        @Nested
        @DisplayName("요청 파라미터가 null인 경우")
        class RequestParameterIsNull {
            @Test
            @DisplayName("adsf")
            void asdf() {
                HttpRequest httpRequest = HttpRequest.builder()
                        .addParameter("userId", null)
                        .addParameter("password", null)
                        .addParameter("name", null)
                        .addParameter("email", null)
                        .build();

                HttpResponse httpResponse = HttpResponse.getInstance();

                //when
                userController.signUp(httpRequest, httpResponse);

                //then
                assertThat(httpResponse.getStatus())
                        .isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
