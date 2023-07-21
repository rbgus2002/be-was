package exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotFoundExtensionExceptionTest {

    @Test
    @DisplayName("NotFoundExtensionException의 메시지가 올바르게 설정되는지 확인한다")
    public void testNotFoundExtensionExceptionMessage() {
        //given
        String expectedMessage = "올바르지 않은 확장자입니다.";

        //when
        NotFoundExtensionException exception = new NotFoundExtensionException();

        //then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
