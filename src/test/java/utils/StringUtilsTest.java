package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.StringUtils.appendNewLine;

class StringUtilsTest {
    @Test
    @DisplayName("\"line.separator\"를 사용해 줄바꿈할 수 있어야 한다")
    void appendNewLineTest() {
        // Given
        String messageHello = "Hello, World!";
        String expectedMessage = "Hello, World!\n";

        // When
        String actualMessage = appendNewLine(messageHello);

        // Then
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}