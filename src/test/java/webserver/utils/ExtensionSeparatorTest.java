package webserver.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionSeparatorTest {

    @DisplayName("파일 이름에서 확장자를 분리하는 기능 테스트")
    @Test
    void separateExtensionTest() {
        String fileName = "/js/jquery-2.2.0.min.js";

        String ext = ExtensionSeparator.separateExtension(fileName);

        assertEquals("js", ext);
    }
}
