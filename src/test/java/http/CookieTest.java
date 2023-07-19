package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {

    @Test
    @DisplayName("http header의 쿠키를 파싱해서 배열로 반환한다.")
    void parseCookie() {
        //given
        String cookieString = "sid=12345; name=god";

        //when
        List<Cookie> cookies = Cookie.parseCookie(cookieString);

        //then
        Cookie cookie1 = new Cookie("sid", "12345");
        Cookie cookie2 = new Cookie("name", "god");
        List<Cookie> expectedCookies = List.of(cookie1, cookie2);

        assertThat(cookies).usingRecursiveComparison().isEqualTo(expectedCookies);
    }
}