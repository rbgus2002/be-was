package utils;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class GMTStringConverterTest {

    @Test
    @DisplayName("LocalDateTime 정보를 GMT로 변환한다.")
    void convertToGMT() {
        LocalDateTime now = LocalDateTime.of(2023, 7, 25, 17, 20);
        String gmtString = GMTStringConverter.convertToGMTString(now);
        String expectGmtString = "Tue, 25 Jul 2023 17:20:00 GMT";

        Assertions.assertEquals(gmtString, expectGmtString);
    }


}