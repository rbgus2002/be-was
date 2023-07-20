package user.controller;

import db.Database;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import user.service.UserService;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

import static org.assertj.core.api.Assertions.*;


@DisplayName("User 도메인 컨트롤러 테스트")
class UserControllerTest {
    final byte[] body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
            .getBytes();
    UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(new UserService());
    }

    @Nested
    @DisplayName("signUp method")
    class SignUp {
        @Nested
        @DisplayName("요청 바디가 올바른 경우")
        class RequestParametersNotNull {
            @Test
            @DisplayName("요청으로부터 파라미터를 받아 유저 객체를 생성하고 데이터베이스에 저장한다")
            void createUserByRequestParametersAndSaveToDatabase() {
                //given
                HttpRequest httpRequest = HttpRequest.builder()
                        .addHeader("Content-Length", String.valueOf(body.length))
                        .body(body)
                        .build();

                HttpResponse httpResponse = HttpResponse.getInstance();

                //when
                userController.signUp(httpRequest, httpResponse);

                //then
                User user = Database.findUserById("userId").orElseThrow(RuntimeException::new);
                assertThat(user)
                        .isEqualTo(new User("userId", "password", "name", "email"));
            }
        }

        @Nested
        @DisplayName("요청 바디가 잘못된 경우")
        class RequestParameterIsNull {
            @Test
            @DisplayName("BAD_REQUEST가 발생한다")
            void occurBAD_REQUESET() {
                HttpRequest httpRequest = HttpRequest.builder()
                        .body("userId=&password=asdf".getBytes())
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
