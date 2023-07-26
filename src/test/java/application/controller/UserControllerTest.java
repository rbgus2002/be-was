package application.controller;

import db.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.response.HttpResponseMessage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("UserController 클래스 테스트")
class UserControllerTest {
    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @BeforeAll
    static void beforeAll() {
        Database.clear();
    }


    @Test
    @DisplayName("유저를 추가한다.")
    void createUser() {
        // given
        String userId = "abc123";
        String password = "password12345";
        String name = "honggildong";
        String email = "abc123@gmail.com";

        // when
        Object returnValue = userController.createUser(userId, password, name, email);

        // then
        assertAll(
                () -> assertEquals(returnValue.getClass(), String.class),
                () -> assertEquals(returnValue, "redirect:/index.html"),
                () -> assertEquals(Database.findUserById(userId).getUserId(), userId)
        );
    }

    @Test
    @DisplayName("중복된 유저는 추가할 수 없다.")
    void createExistUser() {
        // given
        String userId = "abcd";
        String password = "password12345";
        String name = "honggildong";
        String email = "abc123@gmail.com";

        // when
        userController.createUser(userId, password, name, email);

        // then
        assertEquals("redirect:/user/form_failed.html", userController.createUser(userId, password, name, email));
    }

    @Test
    @DisplayName("유저가 회원 가입을 하고, 로그인을 성공한다.")
    void loginSuccess() {
        // given
        String userId = "abcd";
        String password = "password12345";
        String name = "honggildong";
        String email = "abc123@gmail.com";
        userController.createUser(userId, password, name, email);

        // then
        assertEquals("redirect:/index.html", userController.loginUser(userId, password, new HttpResponseMessage()));
    }

    @Test
    @DisplayName("유저가 회원 가입을 하고, 로그인을 실패한다.")
    void loginFail() {
        // given
        String userId = "abcd";
        String password = "password12345";
        String name = "honggildong";
        String email = "abc123@gmail.com";

        userController.createUser(userId, password, name, email);

        String wrongPassword = "notpassword";

        // then
        assertEquals("redirect:/user/login_failed.html", userController.loginUser(userId, wrongPassword, new HttpResponseMessage()));
    }
}
