package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CookieTest {
    @Test
    @DisplayName("cookie toString 형식 확인")
    void isValidToString(){
        // given
        // when
        Cookie cookie = Cookie.from("sid", UUID.randomUUID().toString());

        // then
        System.out.println("cookie.toString() = " + cookie);
    }

    @Test
    @DisplayName("쿠키를 파싱해서 리스트에 담아 반환한다")
    void parse(){
        // given
        List<Cookie> cookies = Cookie.parse("Idea-b49a3250=4870f825-f607-4ca3-8fdf-aa4b2949cb71; sid=6d64096c-6f8a-4136-b283-719888edd6d8");

        // when
        boolean existSid = false;
        for(Cookie cookie : cookies){
            if("sid".equals(cookie.getName())){
                existSid = true;
            }
        }

        // then
        assertTrue(existSid);
    }
}