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

    @Test
    @DisplayName("equals 메서드를 검증한다.")
    void testEquals() {
        //given
        User user1 = new User("testUser", "testPassword", "Test User", "test@example.com");
        User user2 = new User("testUser", "testPassword", "Test User", "test@example.com");

        //when&then
        assertTrue(user1.equals(user2));
    }

    @Test
    @DisplayName("hashcode 메서드를 검증한다.")
    void testHashcode() {
        // given
        User user1 = new User("testUser", "testPassword", "Test User", "test@example.com");
        User user2 = new User("testUser", "testPassword", "Test User", "test@example.com");

        // when & then
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
