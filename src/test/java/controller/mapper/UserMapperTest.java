package controller.mapper;

import controller.dto.UserResponse;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("User Mapper Test")
public class UserMapperTest {

    @Test
    @DisplayName("User 클래스로 변경하는 기능 테스트")
    public void testToUser() {
        //given
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "user123");
        parameters.put("password", "password123");
        parameters.put("name", "John Doe");
        parameters.put("email", "john.doe@example.com");

        //when
        User user = UserMapper.toUser(parameters);

        //then
        assertEquals("user123", user.getUserId());
        assertEquals("password123", user.getPassword());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    @DisplayName("UserResponse로 변경하는 기능 테스트")
    public void testToResponse() {
        //given
        User user = new User("user123", "password123", "John Doe", "john.doe@example.com");

        //when
        UserResponse userResponse = UserMapper.toResponse(user);

        //then
        assertEquals("user123", userResponse.getUserId());
        assertEquals("John Doe", userResponse.getName());
        assertEquals("john.doe@example.com", userResponse.getEmail());
    }
}
