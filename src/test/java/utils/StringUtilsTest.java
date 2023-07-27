package utils;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static utils.StringUtils.appendNewLine;
import static utils.StringUtils.decodeBody;

class StringUtilsTest {

    private SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("\"line.separator\"를 사용해 줄바꿈할 수 있어야 한다")
    void appendNewLineTest() {
        // Given
        String messageHello = "Hello, World!";
        String expectedMessage = "Hello, World!\n";

        // When
        String actualMessage = appendNewLine(messageHello);

        // Then
        softAssertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("인코딩된 텍스트 데이터를 다시 디코딩할 수 있어야 한다")
    void decodeBodyTest() {
        // Given
        String encoded = "%EA%B9%80%EC%95%84%ED%98%84";

        // When
        String actualDecoded = decodeBody(encoded);

        // Then
        softAssertions.assertThat(actualDecoded).isEqualTo("김아현");
    }

}