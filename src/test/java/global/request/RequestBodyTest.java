package global.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("RequestBody 테스트")
class RequestBodyTest {

    @Test
    @DisplayName("getBody 메서드 테스트")
    void testGetBody() {
        //given
        String requestBody = "Hello, World!";
        RequestBody body = new RequestBody(requestBody);

        //when
        String expectedBody = "Hello, World!";
        String actualBody = body.getBody();

        //then
        assertEquals(expectedBody, actualBody);
    }
}
