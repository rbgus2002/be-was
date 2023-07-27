package controller.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserResponseTest {

    @Test
    @DisplayName("UserResponse 생성에 성공한다.")
    public void testUserResponse() {
        //given
        String userId = "user123";
        String name = "John Doe";
        String email = "john.doe@example.com";

        //when
        UserResponse userResponse = new UserResponse(userId, name, email);

        //then
        assertEquals(userId, userResponse.getUserId());
        assertEquals(name, userResponse.getName());
        assertEquals(email, userResponse.getEmail());
    }
}
