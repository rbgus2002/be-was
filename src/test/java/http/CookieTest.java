package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}