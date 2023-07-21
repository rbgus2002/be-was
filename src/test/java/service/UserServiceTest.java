package service;

import db.Database;
import model.User;
import model.UserParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @DisplayName("UserService로 사용자를 생성할 수 있다.")
    void testRegisterUser() {
        // Given
        Map<String, String> params = new HashMap<>();
        params.put(UserParam.ID, "user123");
        params.put(UserParam.PASSWORD, "pass123");
        params.put(UserParam.NAME, "John Doe");
        params.put(UserParam.EMAIL, "john.doe@example.com");

        // When
        userService.register(params);

        // Then
        User user = Database.findUserById("user123");
        assertEquals("pass123", user.getPassword());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
    }
}
