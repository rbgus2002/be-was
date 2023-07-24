package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {
    @Test
    @DisplayName("인코딩 되어있는 문자열을 UTF-8 방식으로 디코딩 한다")
    void decode(){
        // given
        String encode = "%EC%B5%9C%EA%B7%9C%ED%98%84";

        // when
        String decoded = StringUtils.decode(encode);

        // then
        assertEquals("최규현", decoded);
    }
}