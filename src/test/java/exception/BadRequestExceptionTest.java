package exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadRequestExceptionTest {

    @Test
    @DisplayName("BadRequestException의 메시지가 올바르게 설정되는지 확인한다")
    public void testBadRequestExceptionMessage() {
        //given
        String expectedMessage = "올바르지 않은 경로입니다.";

        //when
        BadRequestException exception = new BadRequestException();

        //then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
