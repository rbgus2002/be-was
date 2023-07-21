package global.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RequestHeader 테스트")
class RequestHeaderTest {

    @Test
    @DisplayName("지정한 키가 존재하는 경우, contains 메서드는 true를 반환한다.")
    void testContainsWithExistingKey() {
        //given
        String headerLines = "Content-Type: text/html\r\nLocation: /index.html\r\n";
        RequestHeader requestHeader = new RequestHeader(headerLines);

        //when&then
        assertTrue(requestHeader.contains("Content-Type"));
    }

    @Test
    @DisplayName("지정한 키가 존재하는 않는 경우, contains 메서드는 false를 반환한다.")
    void testContainsWithNonExistingKey() {
        //given
        String headerLines = "Content-Type: text/html\r\nLocation: /index.html\r\n";
        RequestHeader requestHeader = new RequestHeader(headerLines);

        //when&then
        assertFalse(requestHeader.contains("Authorization"));
    }

    @Test
    @DisplayName("지정한 키의 값 반환한다.")
    void testGetWithValue() {
        //given
        String headerLines = "Content-Type: text/html\r\nLocation: /index.html\r\n";
        RequestHeader requestHeader = new RequestHeader(headerLines);

        //when
        String expected = "text/html";
        String actual = requestHeader.get("Content-Type");

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("지정한 키의 값이 존재하지 않는 경우 null 반환한다.")
    void testGetWithNullValue() {
        //given
        String headerLines = "Content-Type: text/html\r\nLocation: /index.html\r\n";
        RequestHeader requestHeader = new RequestHeader(headerLines);

        //when&then
        assertNull(requestHeader.get("Authorization"));
    }

    @Test
    @DisplayName("parseHeaders 메서드 - 헤더 문자열을 Map으로 파싱")
    void testParseHeaders() {
        //given
        String headerLines = "Content-Type: text/html\r\nLocation: /index.html\r\n";
        RequestHeader requestHeader = new RequestHeader(headerLines);

        //when
        Map<String, String> expected = Map.of(
                "Content-Type", "text/html",
                "Location", "/index.html"
        );
        Map<String, String> actual = requestHeader.getHeaders();

        //then
        assertEquals(expected, actual);
    }
}
