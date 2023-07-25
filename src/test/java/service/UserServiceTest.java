package service;

import db.Database;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService Test")
class UserServiceTest {

    UserService userService = new UserService();

    @Test
    @DisplayName("유저가 생성되고 DB에 등록되는 것을 확인한다.")
    void registerUser() {
        // given
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "test123");
        parameters.put("password", "password");
        parameters.put("name", "userA");
        parameters.put("email", "test@gmail.com");

        // when
        userService.registerUser(parameters);

        // then
        User user = Database.findUserById("test123");
        assertEquals("password", user.getPassword());
        assertEquals("userA", user.getName());
        assertEquals("test@gmail.com", user.getEmail());
    }
}
