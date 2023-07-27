package view;

import static org.junit.jupiter.api.Assertions.*;

import controller.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisplayName("View 클래스 테스트")
public class ViewTest {
    View view;

    @BeforeEach
    void setup() {
        view = new View();
    }

    @Test
    @DisplayName("index 화면을 테스트한다.")
    public void testIndex() throws IOException {
        // given
        Map<String, Object> viewParameters = new HashMap<>();
        viewParameters.put("name", "John Doe");

        // when
        byte[] result = view.index(viewParameters);

        // then
        String resultString = new String(result);
        assertTrue(resultString.contains("John Doe"));
    }

    @Test
    @DisplayName("list 화면을 테스트한다.")
    public void testList() {
        // given
        Map<String, Object> viewParameters = new HashMap<>();
        UserResponse user1 = new UserResponse("user123", "John Doe", "john.doe@example.com");
        UserResponse user2 = new UserResponse("user456", "Jane Smith", "jane.smith@example.com");
        viewParameters.put("users", List.of(user1, user2));

        // when
        byte[] result = view.list(viewParameters);

        // then
        String resultString = new String(result);
        assertTrue(resultString.contains("user123"));
        assertTrue(resultString.contains("John Doe"));
        assertTrue(resultString.contains("john.doe@example.com"));
        assertTrue(resultString.contains("user456"));
        assertTrue(resultString.contains("Jane Smith"));
        assertTrue(resultString.contains("jane.smith@example.com"));
    }
}
