package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    @DisplayName("사용자 생성에 성공한다.")
    public void testUserGetterMethods() {
        // given
        String userId = "user123";
        String password = "secretpassword";
        String name = "John Doe";
        String email = "john@example.com";

        // when
        User user = new User(userId, password, name, email);

        // then
        assertEquals(userId, user.getUserId());
        assertEquals(password, user.getPassword());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("toString 메서드를 검증한다.")
    public void testToString() {
        // given
        String userId = "user123";
        String password = "secretpassword";
        String name = "John Doe";
        String email = "john@example.com";
        String expectedToString = "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";

        // when
        User user = new User(userId, password, name, email);

        // then
        assertEquals(expectedToString, user.toString());
    }
}
