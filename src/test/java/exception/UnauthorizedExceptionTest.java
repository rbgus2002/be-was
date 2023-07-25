package exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnauthorizedExceptionTest {

    @Test
    @DisplayName("NotFoundExtensionException의 메시지가 올바르게 설정되는지 확인한다")
    public void testUnauthorizedExceptionMessage() {
        //given
        String expectedMessage = "인증되지 않는 사용자입니다.";

        //when
        UnauthorizedException exception = new UnauthorizedException();

        //then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
