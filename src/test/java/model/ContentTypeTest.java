package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentTypeTest {

    ContentType TEXT_PLAIN;
    ContentType TEXT_HTML;
    ContentType TEXT_CSS;
    ContentType TEXT_JAVASCRIPT;

    @BeforeEach
    void setUp() {
         TEXT_PLAIN = ContentType.TEXT_PLAIN;
         TEXT_HTML = ContentType.TEXT_HTML;
         TEXT_CSS = ContentType.TEXT_CSS;
         TEXT_JAVASCRIPT = ContentType.TEXT_JAVASCRIPT;
    }

    @Test
    @DisplayName("Content Type의 출력이 올바른지 테스트")
    void getContentType() {
        assertEquals("text/plain;charset=UTF-8", TEXT_PLAIN.getContentType());
        assertEquals("text/html;charset=UTF-8", TEXT_HTML.getContentType());
        assertEquals("text/css", TEXT_CSS.getContentType());
        assertEquals("text/javascript", TEXT_JAVASCRIPT.getContentType());
    }
}