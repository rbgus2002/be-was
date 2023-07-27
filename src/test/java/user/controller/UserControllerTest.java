package user.controller;

import controller.UserController;
import db.UserTable;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import service.UserService;
import webserver.http.HttpHeaders;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.myframework.session.SessionManager;
import webserver.myframework.session.SessionManagerImpl;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


@DisplayName("User 도메인 컨트롤러 테스트")
class UserControllerTest {
    final byte[] body = "userId=syuaID&password=syuaPW&name=syuaNAME&email=syuaEMAIL"
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
                HttpResponse httpResponse = HttpResponse.getInstance();

                //when
                userController.signUp(new String(body, StandardCharsets.UTF_8), httpResponse);

                //then
                User user = UserTable.findUserById("syuaID");
                assertThat(user)
                        .isEqualTo(new User("syuaID", "syuaPW", "syuaNAME", "syuaEMAIL"));
            }
        }

        @Nested
        @DisplayName("요청 바디가 잘못된 경우")
        class RequestParameterIsNull {
            @Test
            @DisplayName("BAD_REQUEST가 발생한다")
            void occurBAD_REQUESET() {
                HttpResponse httpResponse = HttpResponse.getInstance();

                //when
                userController.signUp("userId=&password=asdf", httpResponse);

                //then
                assertThat(httpResponse.getStatus())
                        .isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Nested
    @DisplayName("signIn method")
    class SignIn {
        SessionManager sessionManager;

        @SuppressWarnings("unchecked")
        @BeforeEach
        void setUp() throws ReflectiveOperationException {
            Field usersField = UserTable.class.getDeclaredField("users");
            usersField.setAccessible(true);
            ((Map<String, User>) usersField.get(null)).clear();

            UserTable.addUser(new User("exist", "exist", "exist", "exist@exist"));
            sessionManager = new SessionManagerImpl();
        }

        @Nested
        @DisplayName("존재하는 사용자인 경우")
        class IsUserExist {
            @Test
            @DisplayName("302 상태코드와 함께 Location 헤더를 /index.html로 설정한다")
            void setLocationHeaderToIndex() {
                //given
                HttpRequest httpRequest = HttpRequest.builder(sessionManager)
                        .uri("/user/login")
                        .method(HttpMethod.POST)
                        .body("userId=exist&password=exist".getBytes())
                        .build();

                HttpResponse httpResponse = HttpResponse.getInstance();

                //when
                userController.signIn(httpRequest, httpResponse);

                //then
                HttpHeaders headers = httpResponse.getHeaders();
                assertThat(headers.getHeaderValues("Location")).isEqualTo("/index.html");
                assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 사용자인 경우")
        class IsUserNotExist {
            @Test
            @DisplayName("302 상태코드와 함께 Location 헤더를 /user/login_failed.html로 설정한다")
            void setLocationHeaderToFail() {
                //given
                HttpRequest httpRequest = HttpRequest.builder(sessionManager)
                        .uri("/user/login")
                        .method(HttpMethod.POST)
                        .body("userId=notExist&password=notExist".getBytes())
                        .build();

                HttpResponse httpResponse = HttpResponse.getInstance();

                //when
                userController.signIn(httpRequest, httpResponse);

                //then
                HttpHeaders headers = httpResponse.getHeaders();
                assertThat(headers.getHeaderValues("Location")).isEqualTo("/user/login_failed.html");
                assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
            }
        }

        @Nested
        @DisplayName("잘못된 입력이 들어갈 경우")
        class WhenWrongInputGiven {
            @Test
            @DisplayName("상태 코드를 400으로 설정한다")
            void setStatus400() {
                //given
                HttpRequest httpRequest = HttpRequest.builder(sessionManager)
                        .uri("/user/login")
                        .method(HttpMethod.POST)
                        .body("userId=&password=exist".getBytes())
                        .build();

                HttpResponse httpResponse = HttpResponse.getInstance();

                //when
                userController.signIn(httpRequest, httpResponse);

                //then
                assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
            }
        }
    }
}
