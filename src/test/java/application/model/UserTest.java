package application.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Entity 클래스 테스트.")
class UserTest {
    @Test
    @DisplayName("유저 객체를 생성한다.")
    void createUser() {
        // given
        String userId = "abc123";
        String password = "password12345";
        String name = "honggildong";
        String email = "abc123@gmail.com";

        // when
        User user = new User(userId, password, name, email);

        // then
        assertAll(
                () -> assertEquals(userId, user.getUserId()),
                () -> assertEquals(password, user.getPassword()),
                () -> assertEquals(name, user.getName()),
                () -> assertEquals(email, user.getEmail())
        );
    }

    @Test
    @DisplayName("userId가 존재하지 않다면 유저를 생성할 수 없다.")
    void verifyCreateUser() {
        // given
        String userId = "";
        String password = "asd";
        String name = "honggildong";
        String email = "abc123@gmail.com";

        // then
        assertThrows(IllegalArgumentException.class,
                () -> new User(userId, password, name, email));
    }
}
