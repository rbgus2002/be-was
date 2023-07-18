package global.constant;

import exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HttpMethodTest {

    @Test
    @DisplayName("주어진 메소드 이름으로 올바른 HttpMethod를 찾는다")
    public void testFindHttpMethod() {
        //given
        String methodName = "GET";

        //when&then
        assertEquals(HttpMethod.GET, HttpMethod.findHttpMethod(methodName));
    }

    @Test
    @DisplayName("주어진 잘못된 메소드 이름으로 Exception이 발생한다")
    public void testFindHttpMethodWithInvalidMethodName() {
        //given
        String wrongMethodName = "TEST";

        //
        assertThrows(BadRequestException.class, () -> HttpMethod.findHttpMethod(wrongMethodName));
    }
}
