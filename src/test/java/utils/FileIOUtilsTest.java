package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.FileIOUtils.splitPath;

class FileIOUtilsTest {

    @Test
    @DisplayName("HTTP Request Header의 첫번째 줄에서 파일명을 얻어야 한다")
    void splitPathTest() {
        assertEquals("./index.html", splitPath("GET ./index.html HTTP/1.1"));
    }

}