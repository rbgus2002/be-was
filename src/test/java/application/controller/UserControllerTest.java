package application.controller;

import db.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserController 클래스 테스트")
class UserControllerTest {
    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
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
        assertThrows(IllegalArgumentException.class,
                () -> userController.createUser(userId, password, name, email));
    }
}
