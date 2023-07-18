package global.constant;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeadersTest {

    @Test
    @DisplayName("각 헤더의 키를 올바르게 반환한다")
    public void testGetKey() {
        //given
        SoftAssertions softAssertions = new SoftAssertions();

        //when&then
        softAssertions.assertThat("Content-Length").as("Content-Length 검증한다.").isEqualTo(Headers.CONTENT_LENGTH.getKey());
        softAssertions.assertThat("Location").as("Content-Length 검증한다.").isEqualTo(Headers.LOCATION.getKey());
        softAssertions.assertThat("Content-Type").as("Content-Length 검증한다.").isEqualTo(Headers.CONTENT_TYPE.getKey());
    }
}
