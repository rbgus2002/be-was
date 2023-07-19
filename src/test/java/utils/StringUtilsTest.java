package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    @DisplayName("인코딩 된 문자열을 입력하면 디코딩 된 형태로 반환되어야 한다.")
    void decodeString (){
        assertEquals("javajigi@slipp.net", StringUtils.getDecodedString("javajigi%40slipp.net"));
        assertEquals("박재성", StringUtils.getDecodedString("%EB%B0%95%EC%9E%AC%EC%84%B1"));
    }
}