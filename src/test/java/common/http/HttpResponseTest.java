package common.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {

    private HttpResponse response;

    @BeforeEach
    void setUp() {
        response = new HttpResponse();
    }

    @Test
    @DisplayName("헤더를 추가할 수 있다.")
    void addHeader() {
        response.addHeader("k1", "v2");

        String stringHeaders = response.toStringHeaders();

        assertEquals("k1: v2\r\n", stringHeaders);
    }

    @Test
    @DisplayName("쿠키를 추가할 수 있다.")
    void addCookie() {
        response.addCookie(Cookie.of("sid", "1234", "/"));

        String stringHeaders = response.toStringHeaders();

        assertEquals("Set-Cookie: sid=1234; Path=/; \r\n", stringHeaders);
    }
}