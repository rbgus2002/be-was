package webserver;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHandlerTest {
    @BeforeAll
    public void setup() throws Exception {
        WebServer.main(new String[]{});
    }

    @Test
    @DisplayName("유저가 회원가입하면 Database에 계정이 생성된다")
    public void signUp() {
        // Given
        System.out.println("hello!");
    }
}
